//
//  AddAppointmentInviteesViewController.swift
//  Accounts
//
//  Created by Alexander Swanson on 6/27/19.
//  Copyright © 2019 Oxygen. All rights reserved.
//

import UIKit
import CoreData

class NewAppointmentInviteesTableViewController: UITableViewController, NSFetchedResultsControllerDelegate {

    var appDelegate = UIApplication.shared.delegate as! AppDelegate
    weak var prevViewControllerDelegate: AppointmenCreationtTableViewController!
    var fetchedResultsController: NSFetchedResultsController<ClientMO>!
    var invitees: [ClientMO]!
    var clients: [ClientMO]?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initializeFetchedResultsController()
        clients = fetchedResultsController.fetchedObjects
    }
    
    func initializeFetchedResultsController() {
        let request = NSFetchRequest<ClientMO>(entityName: "Client")
        let firstNameSort = NSSortDescriptor(key: "firstName", ascending: true)
        request.sortDescriptors = [firstNameSort]
        
        fetchedResultsController = NSFetchedResultsController(fetchRequest: request, managedObjectContext: appDelegate.managedObjectContext!, sectionNameKeyPath: "orderIndex", cacheName: nil)
        fetchedResultsController.delegate = self
        
        do {
            try fetchedResultsController.performFetch()
        } catch {
            fatalError("Failed to initialize FetchedResultsController: \(error)")
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        prevViewControllerDelegate.updateSelectedInvitees(invitees)
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return fetchedResultsController.sections!.count
    }
    
    // Returns the letter of the section. This is the first letter of Client names in the respective section.
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        guard let sectionInfo = fetchedResultsController.sections?[section] else {
            return nil
        }
        return sectionInfo.name
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        guard let sections = fetchedResultsController.sections else {
            fatalError("No sections in fetchedResultsController")
        }
        
        let sectionInfo = sections[section]
        return sectionInfo.numberOfObjects
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "newAppointmentInviteesCell", for: indexPath)
        
        // Set up the cell
        guard let client = self.fetchedResultsController?.object(at: indexPath) else {
            fatalError("Attempt to configure cell without a managed object")
        }
        let lastNameText = client.lastName ?? ""
        cell.textLabel?.text = client.firstName + " " + lastNameText
        
        if invitees.contains(fetchedResultsController.object(at: indexPath)) {
            cell.accessoryType = .checkmark
        }
        
        return cell
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        toggleClientRowSelection(tableView, forCellAt: indexPath)
    }
    
    func toggleClientRowSelection(_ tableView: UITableView, forCellAt indexPath: IndexPath) {
        if let cell = tableView.cellForRow(at: indexPath) {
            if cell.accessoryType == .none {
                cell.accessoryType = .checkmark
                addClientAsInvitee(forClientAt: indexPath)
            } else {
                cell.accessoryType = .none
                removeClientAsInvitee(forClientAt: indexPath)
            }
        }
        tableView.deselectRow(at: indexPath, animated: true)
    }
    
    func controllerWillChangeContent(_ controller: NSFetchedResultsController<NSFetchRequestResult>) {
        tableView.beginUpdates()
    }
    
    func controller(_ controller: NSFetchedResultsController<NSFetchRequestResult>, didChange sectionInfo: NSFetchedResultsSectionInfo, atSectionIndex sectionIndex: Int, for type: NSFetchedResultsChangeType) {
        switch type {
        case .insert:
            tableView.insertSections(IndexSet(integer: sectionIndex), with: .fade)
        case .delete:
            tableView.deleteSections(IndexSet(integer: sectionIndex), with: .fade)
        case .move:
            break
        case .update:
            break
        @unknown default:
            fatalError()
        }
    }
    
    func controller(_ controller: NSFetchedResultsController<NSFetchRequestResult>, didChange anObject: Any, at indexPath: IndexPath?, for type: NSFetchedResultsChangeType, newIndexPath: IndexPath?) {
        switch type {
        case .insert:
            tableView.insertRows(at: [newIndexPath!], with: .fade)
        case .delete:
            tableView.deleteRows(at: [indexPath!], with: .fade)
        case .update:
            tableView.reloadRows(at: [indexPath!], with: .fade)
        case .move:
            tableView.moveRow(at: indexPath!, to: newIndexPath!)
        @unknown default:
            fatalError()
        }
    }
    
    func controllerDidChangeContent(_ controller: NSFetchedResultsController<NSFetchRequestResult>) {
        tableView.endUpdates()
    }
    
    func addClientAsInvitee(forClientAt clientCellIndexPath: IndexPath) {
        let client = fetchedResultsController.object(at: clientCellIndexPath)
        if !invitees.contains(client) {
            invitees.append(client)
        }
    }
    
    func removeClientAsInvitee(forClientAt clientCellIndexPath: IndexPath) {
        let client = fetchedResultsController.object(at: clientCellIndexPath)
        if let index = invitees.firstIndex(of: client) {
            invitees.remove(at: index)
        }
    }

}
