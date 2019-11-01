//
//  ClientDetailAppointmentsTableViewController.swift
//  Accounts
//
//  Created by Alexander Swanson on 7/1/19.
//  Copyright © 2019 Oxygen. All rights reserved.
//

/*
 Defining cells for appointment info
 */

import UIKit

class ClientDetailAppointmentsTableViewController: UITableViewController {

    weak var appDelegate = UIApplication.shared.delegate as? AppDelegate
    var respectiveClient: ClientMO!
    var appointments: [Int: AppointmentMO]?
    var previousAppointments: [AppointmentMO]!
    var futureAppointments: [AppointmentMO]!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.rightBarButtonItem = self.editButtonItem
        
        previousAppointments = [AppointmentMO]()
        futureAppointments = [AppointmentMO]()
        
        if let fetchedAppointments = respectiveClient.appointments?.allObjects as? [AppointmentMO] {
            let now = Date()
            for appointment in fetchedAppointments {
                if appointment.startDate!.compare(now) == .orderedAscending {
                    previousAppointments.append(appointment)
                } else {
                    futureAppointments.append(appointment)
                }
            }
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        appDelegate?.dataController.saveContext()
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if section == 0 {
            let count = futureAppointments.count
            if count != 0 {
                return count
            }
        } else if section == 1 {
            let count = previousAppointments.count
            if count != 0 {
                return count
            }
        }
        return 1
    }
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        if section == 0 {
            return "Upcoming"
        } else {
            return "Previous"
        }
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell = tableView.dequeueReusableCell(withIdentifier: "AppointmentDetailCell", for: indexPath)

        if indexPath.section == 0 {
            // upcoming
            if futureAppointments.isEmpty {
                cell.textLabel?.text = "No future appointments"
                cell.textLabel?.textAlignment = .center
            } else {
                let appointment = futureAppointments[indexPath.row]
                cell = UITableViewCell(style: .value1, reuseIdentifier: "AppointmentDetailCell")
                cell.textLabel?.text = appointment.name
                cell.detailTextLabel?.text = formatDate(of: appointment.startDate!)
            }
            
        } else if indexPath.section == 1 {
            // previous
            if previousAppointments.isEmpty {
                cell.textLabel?.text = "No past appointments"
                cell.textLabel?.textAlignment = .center
            } else {
                let appointment = previousAppointments[indexPath.row]
                cell = UITableViewCell(style: .value1, reuseIdentifier: "AppointmentDetailCell")
                cell.textLabel?.text = appointment.name
                cell.detailTextLabel?.text = formatDate(of: appointment.startDate!)
            }
        }

        return cell
    }

    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            controllerWillChangeContent()
            
            var appointment: AppointmentMO
            var doReloadRows: Bool
            if indexPath.section == 0 {
                appointment = futureAppointments[indexPath.row]
                futureAppointments.remove(at: indexPath.row)
                doReloadRows = futureAppointments.isEmpty
            } else {
                appointment = previousAppointments[indexPath.row]
                previousAppointments.remove(at: indexPath.row)
                doReloadRows = previousAppointments.isEmpty
            }
            respectiveClient.removeFromAppointments(appointment)
            
            if doReloadRows {
                tableView.reloadRows(at: [indexPath], with: .fade)
            } else {
                tableView.deleteRows(at: [indexPath], with: .fade)
            }
            
            controllerDidChangeContent()
        }
    }
    
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        if indexPath.section == 0  && futureAppointments.isEmpty {
            return false
        } else if indexPath.section == 1 && previousAppointments.isEmpty {
            return false
        }
        return true
    }
    
    func formatDate(of date: NSDate) -> String {
        return DateFormatter.localizedString(from: date as Date, dateStyle: DateFormatter.Style.short, timeStyle: DateFormatter.Style.short)
    }

    func controllerWillChangeContent() {
        tableView.beginUpdates()
    }
    
    func controllerDidChangeContent() {
        tableView.endUpdates()
    }

}
