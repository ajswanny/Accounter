//
//  NewClientViewController.swift
//  Accounts
//
//  Created by Alexander Swanson on 6/17/19.
//  Copyright © 2019 Oxygen. All rights reserved.
//

import UIKit

class NewClientViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func cancelNewClientCreationAndCloseView(_ sender: UIBarButtonItem) {
        dismiss(animated: true, completion: nil)
    }
}

