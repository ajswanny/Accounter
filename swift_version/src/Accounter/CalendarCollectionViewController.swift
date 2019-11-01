//
//  CalendarCollectionViewController.swift
//  Accounts
//
//  Created by Alexander Swanson on 7/2/19.
//  Copyright © 2019 Oxygen. All rights reserved.
//

import UIKit
import CoreData

private let reuseIdentifier = "Cell"

class CalendarCollectionViewController: UICollectionViewController, NSFetchedResultsControllerDelegate, UICollectionViewDelegateFlowLayout {
    
    var appDelegate = UIApplication.shared.delegate as! AppDelegate
    var fetchedResultsController: NSFetchedResultsController<AppointmentMO>!
    var currentMonthLabel: UILabel!
    var currentYearLabel: UILabel!
    var mondayLabel: UILabel!
    var tuesdayLabel: UILabel!
    var wednesdayLabel: UILabel!
    var thursdayLabel: UILabel!
    var fridayLabel: UILabel!
    var saturdayLabel: UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Register cell classes
        self.collectionView!.register(UICollectionViewCell.self, forCellWithReuseIdentifier: reuseIdentifier)

        initializeFetchedResultsController()

    }
    
    
    
    func createLabelForWeekdayName(ofWeekday weekday: String) -> UILabel {
        let label = UILabel()
        label.text = weekday
        label.textAlignment = .center
        label.backgroundColor = UIColor.clear
        label.textColor = UIColor.black
        label.font = UIFont.systemFont(ofSize: 12)
//        label.layer.borderWidth = 1
//        label.layer.borderColor = UIColor.red.cgColor
        label.sizeToFit()
        
        return label
    }

    // MARK: UICollectionViewDataSource
    
    func initializeFetchedResultsController() {
        let request = NSFetchRequest<AppointmentMO>(entityName: "Appointment")
        let startDateSort = NSSortDescriptor(key: "startDate", ascending: true)
        request.sortDescriptors = [startDateSort]
        
        fetchedResultsController = NSFetchedResultsController(fetchRequest: request, managedObjectContext: appDelegate.managedObjectContext!, sectionNameKeyPath: nil, cacheName: nil)
        fetchedResultsController.delegate = self
        
        do {
            try fetchedResultsController.performFetch()
        } catch {
            fatalError("Failed to initialize FetchedResultsController: \(error)")
        }
    }

    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }


    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of items
        return 7
    }

    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
//        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: reuseIdentifier, for: indexPath)
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: reuseIdentifier, for: indexPath)
    
        // Configure the cell
        cell.layer.borderColor = UIColor.lightGray.cgColor
        cell.layer.borderWidth = 1
        
        let label = UILabel()
        label.text = "Hi"
        
        cell.addSubview(label)
    
        return cell
    }

    // MARK: UICollectionViewDelegate
    
    func collectionView(_ collectionView: UICollectionView,
                        layout collectionViewLayout: UICollectionViewLayout,
                        sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: collectionView.bounds.size.width/7, height: collectionView.bounds.size.height)
    }

    /*
    // Uncomment this method to specify if the specified item should be highlighted during tracking
    override func collectionView(_ collectionView: UICollectionView, shouldHighlightItemAt indexPath: IndexPath) -> Bool {
        return true
    }
    */

    /*
    // Uncomment this method to specify if the specified item should be selected
    override func collectionView(_ collectionView: UICollectionView, shouldSelectItemAt indexPath: IndexPath) -> Bool {
        return true
    }
    */

    /*
    // Uncomment these methods to specify if an action menu should be displayed for the specified item, and react to actions performed on the item
    override func collectionView(_ collectionView: UICollectionView, shouldShowMenuForItemAt indexPath: IndexPath) -> Bool {
        return false
    }

    override func collectionView(_ collectionView: UICollectionView, canPerformAction action: Selector, forItemAt indexPath: IndexPath, withSender sender: Any?) -> Bool {
        return false
    }

    override func collectionView(_ collectionView: UICollectionView, performAction action: Selector, forItemAt indexPath: IndexPath, withSender sender: Any?) {
    
    }
    */

}
