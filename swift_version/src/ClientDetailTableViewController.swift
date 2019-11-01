//
//  ClientDetailViewController.swift
//  Accounts
//
//  Created by Alexander Swanson on 6/28/19.
//  Copyright © 2019 Oxygen. All rights reserved.
//

import UIKit

class ClientDetailTableViewController: UITableViewController {

    var respectiveClient: ClientMO!
    var nameIP = IndexPath(row: 0, section: 0)
    var emailAddressIP: IndexPath?
    var phoneNumberIP: IndexPath?
    var notesIP: IndexPath?
    var appointmentsButtonIP: IndexPath?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        emailAddressIP = IndexPath(row: 0, section: 1)
        phoneNumberIP = IndexPath(row: 1, section: 1)
        notesIP = IndexPath(row: 2, section: 1)
        appointmentsButtonIP = IndexPath(row: 0, section: 2)
        
    }

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 3
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        switch section {
        case 0:
            return 1
        case 1:
            return 3
        case 2:
            return 1
        default:
            return 0
        }
    }
    
    func clientDetailItemCell(titled subtitleText: String, using contentText: String) -> UITableViewCell {
        let cell = UITableViewCell(style: .subtitle, reuseIdentifier: "clientDetailCell")
        
        cell.selectionStyle = .none
        
        cell.textLabel?.text = subtitleText
        cell.textLabel?.font = UIFont.systemFont(ofSize: 11)
        
        cell.detailTextLabel?.text = contentText
        cell.detailTextLabel?.font = UIFont.systemFont(ofSize: 14)
        
        return cell
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        var cell = tableView.dequeueReusableCell(withIdentifier: "clientDetailCell", for: indexPath)
        
        switch indexPath {
        case nameIP:
            cell.textLabel?.text = respectiveClient.getFullName()
            cell.textLabel?.textAlignment = .center
            cell.textLabel?.font = UIFont.systemFont(ofSize: 25)
            break
        case emailAddressIP:
            if let email = respectiveClient.emailAddress.nilIfEmpty {
                cell = clientDetailItemCell(titled: "email", using: email)
            }
            break
        case phoneNumberIP:
            if let phoneNumber = respectiveClient.phoneNumber.nilIfEmpty {
                cell = clientDetailItemCell(titled: "phone", using: phoneNumber)
            }
            break
        case notesIP:
            if let notes = respectiveClient.notes.nilIfEmpty {
                cell = clientDetailItemCell(titled: "notes", using: notes)
            }
            break
        case appointmentsButtonIP:
            cell.textLabel?.text = "Appointments"
            cell.accessoryType = .disclosureIndicator
            cell.textLabel?.font = UIFont.systemFont(ofSize: 14)
            break
        default:
            return cell
        }
        
        return cell
    }
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        switch indexPath {
        case emailAddressIP:
            if respectiveClient.emailAddress.nilIfEmpty == nil {
                return 0
            }
        case phoneNumberIP:
            if respectiveClient.phoneNumber.nilIfEmpty == nil {
                return 0
            }
        case notesIP:
            if respectiveClient.notes.nilIfEmpty == nil {
                return 0
            }
        default:
            break
        }
        return super.tableView(tableView, heightForRowAt: indexPath)
    }
    
    override func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        return 10
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "ShowClientAppointments" {
            if let destination = segue.destination as? ClientDetailAppointmentsTableViewController {
                destination.respectiveClient = self.respectiveClient
            }
        }
    }

}

extension Optional where Wrapped == String {
    var nilIfEmpty: String? {
        guard let strongSelf = self else {
            return nil
        }
        return strongSelf.isEmpty ? nil : strongSelf
    }
}
