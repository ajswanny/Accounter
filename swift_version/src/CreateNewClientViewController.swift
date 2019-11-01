//
//  CreateNewClientViewController.swift
//  Accounts
//
//  Created by Alexander Swanson on 6/21/19.
//  Copyright © 2019 Oxygen. All rights reserved.
//

import UIKit

class CreateNewClientViewController: UIViewController {
    
    var appDelegate = UIApplication.shared.delegate as! AppDelegate

    @IBOutlet weak var doneUIButton: UIBarButtonItem!
    @IBOutlet weak var firstNameTextField: UITextField!
    @IBOutlet weak var lastNameTextField: UITextField!
    @IBOutlet weak var phoneNumberTextField: UITextField!
    @IBOutlet weak var emailAddressTextField: UITextField!
    @IBOutlet weak var addressStreetTextField: UITextField!
    @IBOutlet weak var addressCityTextField: UITextField!
    @IBOutlet weak var addressStateTextField: UITextField!
    @IBOutlet weak var addressZipTextField: UITextField!
    @IBOutlet weak var notesTextField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func closeViewAndCancelNewClientCreation(_ sender: UIBarButtonItem) {
        dismiss(animated: true, completion: nil)
    }

    
    @IBAction func firstNameTextFieldEditingChanged(_ sender: UITextField) {
        if firstNameTextField.hasText {
            doneUIButton.isEnabled = true
        } else {
            doneUIButton.isEnabled = false
        }
    }
    
    @IBAction func createNewClient(_ sender: UIBarButtonItem) {
        let newClient = ClientMO(context: appDelegate.dataController.managedObjectContext)
        newClient.firstName = firstNameTextField.text!
        newClient.orderIndex = newClient.firstName.prefix(1).capitalized
        newClient.lastName = lastNameTextField.text
        newClient.phoneNumber = phoneNumberTextField.text
        newClient.emailAddress = emailAddressTextField.text
        let newAddress = AddressMO(context: appDelegate.dataController.managedObjectContext)
        newAddress.street = addressStreetTextField.text
        newAddress.city = addressCityTextField.text
        newAddress.state = addressStateTextField.text
        newAddress.postalCode = addressZipTextField.text
        newClient.address = newAddress
        newClient.notes = notesTextField.text
        
        appDelegate.dataController.saveContext()
        
        dismiss(animated: true, completion: nil)
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
