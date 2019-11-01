//
//  HighlightClientsViewController.swift
//  Accounts
//
//  Created by Alexander Swanson on 6/14/19.
//  Copyright © 2019 Oxygen. All rights reserved.
//

import UIKit

class HighlightClientsViewController: UITableViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 0
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 0
    }

    @IBAction func closeViewAndHighlightClientsIfAppropriate(_ sender: UIBarButtonItem) {
        dismiss(animated: true, completion: nil)
    }
    
}
