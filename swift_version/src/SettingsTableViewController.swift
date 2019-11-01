//
//  SettingsTableViewController.swift
//  Accounts
//
//  Created by Alexander Swanson on 7/3/19.
//  Copyright © 2019 Oxygen. All rights reserved.
//

import UIKit

class SettingsTableViewController: UITableViewController {

    @IBOutlet weak var helpPageCell: UITableViewCell!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        helpPageCell.textLabel?.text = "Help"
        helpPageCell.accessoryType = .disclosureIndicator
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return 1
    }
    
    override func tableView(_ tableView: UITableView, titleForFooterInSection section: Int) -> String? {
        return "Accounts version 1"
    }
    
    // MARK: - IB Actions
    @IBAction func userDidTapCloseButton(_ sender: UIBarButtonItem) {
        self.dismiss(animated: true, completion: nil)
    }
    
}
