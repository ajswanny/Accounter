//
//  CreateNewAppointmentViewController.swift
//  Accounts
//
//  Created by Alexander Swanson on 6/24/19.
//  Copyright © 2019 Oxygen. All rights reserved.
//

import UIKit
import Foundation

class AppointmenCreationtTableViewController: UITableViewController, UITextFieldDelegate {
    
    var appDelegate = UIApplication.shared.delegate as! AppDelegate
    var appointmentInviteesViewController: NewAppointmentInviteesTableViewController!
    var invitees: [ClientMO]?
    let appointmentStartDatePickerIndexPath = IndexPath(row: 1, section: 1)
    let appointmentStartDateLabelIndexPath = IndexPath(row: 0, section: 1)
    let appointmentEndDatePickerIndexPath = IndexPath(row: 3, section: 1)
    let appointmentEndDateLabelIndexPath = IndexPath(row: 2, section: 1)

    @IBOutlet var startDatePicker: UIDatePicker!
    @IBOutlet var appointmentStartDateLabel: UILabel!
    @IBOutlet var appointmentEndDateLabel: UILabel!
    @IBOutlet var endDatePicker: UIDatePicker!
    @IBOutlet weak var doneBarButtonItem: UIBarButtonItem!
    @IBOutlet weak var appointmentNameTextField: UITextField!
    @IBOutlet weak var appointmentLocationTextField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        invitees = [ClientMO]()
        
        appointmentStartDateLabel.text = formatDate(of: startDatePicker)
        startDatePicker.isHidden = true
        
        endDatePicker.date.addTimeInterval(TimeInterval(3600))
        appointmentEndDateLabel.text = formatDate(of: endDatePicker)
        endDatePicker.isHidden = true
    
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        appDelegate.dataController.saveContext()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "IntoNewAppointmentsInviteesSelection" {
            appointmentInviteesViewController = segue.destination as? NewAppointmentInviteesTableViewController
            appointmentInviteesViewController?.prevViewControllerDelegate = self
            appointmentInviteesViewController?.invitees = self.invitees
        }
    }
    
    @IBAction func createNewAppointmentAndCloseView(_ sender: UIBarButtonItem) {
        
        let appointment = AppointmentMO(context: appDelegate.dataController.managedObjectContext)
        
        appointment.name = appointmentNameTextField.text!
        appointment.location = appointmentLocationTextField.text
        appointment.startDate = startDatePicker.date as NSDate
        appointment.dayOfWeekValue = Int16(Calendar.current.component(.weekday, from: startDatePicker.date))
        appointment.endDate = endDatePicker.date as NSDate
        if let attendees = invitees {
            appointment.addToAttendees(NSSet(array: attendees))
        }
        
        dismiss(animated: true, completion: nil)
        
    }
    
    @IBAction func appointmentNameEditingHasChanged(_ sender: UITextField) {
        if sender.hasText {
            doneBarButtonItem.isEnabled = true
        } else {
            doneBarButtonItem.isEnabled = false
        }
    }
    
    @IBAction func cancelNewAppointmentCreationAndCloseView(_ sender: UIBarButtonItem) {
        dismiss(animated: true, completion: nil)
    }
    
    @IBAction func valueOfAppointmentStartDateChanged(_ sender: UIDatePicker) {
        appointmentStartDateLabel.text = formatDate(of: startDatePicker)
        
        if endDatePicker.date.compare(startDatePicker.date) == .orderedAscending {
            endDatePicker.date = startDatePicker.date.addingTimeInterval(3600)
            valueOfAppointmentEndDateChanged(endDatePicker)
        }
        
    }

    @IBAction func valueOfAppointmentEndDateChanged(_ sender: UIDatePicker) {
        appointmentEndDateLabel.text = formatDate(of: endDatePicker)
    }
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath == appointmentStartDatePickerIndexPath {
            let height: CGFloat = startDatePicker.isHidden ? 0.0 : 200
            return height
        } else if indexPath == appointmentEndDatePickerIndexPath {
            let height: CGFloat = endDatePicker.isHidden ? 0.0 : 200
            return height
        } else {
            return super.tableView(tableView, heightForRowAt: indexPath)
        }
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath == appointmentStartDateLabelIndexPath {
            toggleShowDateDatepicker(for: startDatePicker)
            if !endDatePicker.isHidden {
                hideDatePicker(endDatePicker)
            }
        } else if indexPath == appointmentEndDateLabelIndexPath {
            toggleShowDateDatepicker(for: endDatePicker)
            if !startDatePicker.isHidden {
                hideDatePicker(startDatePicker)
            }
        } else {
            hideDatePicker(startDatePicker)
            hideDatePicker(endDatePicker)
        }
        tableView.deselectRow(at: indexPath, animated: true)
    }
    
    func toggleShowDateDatepicker(for datePicker: UIDatePicker) {
        datePicker.isHidden = !datePicker.isHidden
        tableView.beginUpdates()
        tableView.endUpdates()
    }
    
    func hideDatePicker(_ datePicker: UIDatePicker) {
        datePicker.isHidden = true
        tableView.beginUpdates()
        tableView.endUpdates()
    }
    
    func formatDate(of date: UIDatePicker) -> String {
        return DateFormatter.localizedString(from: date.date, dateStyle: DateFormatter.Style.long, timeStyle: DateFormatter.Style.short)
    }
    
    func updateSelectedInvitees(_ newInvitees: [ClientMO]) {
        invitees = newInvitees
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
