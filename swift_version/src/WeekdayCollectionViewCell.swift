//
//  WeekdayCollectionViewCell.swift
//  Accounts
//
//  Created by Alexander Swanson on 7/4/19.
//  Copyright © 2019 Oxygen. All rights reserved.
//

import UIKit
import CoreData

class WeekdayCollectionViewCell: UICollectionViewCell, UITableViewDelegate, UITableViewDataSource, NSFetchedResultsControllerDelegate {
    
    var fetchedResultsController: NSFetchedResultsController<AppointmentMO>!
    weak var managedObjectContext: NSManagedObjectContext!
    var date: Date!
    var tableView: UITableView!
    var appointments: [AppointmentMO]!
    var thing: String!
    var weekday: Int!
    
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    func setData(currentDate: Date, dayOfWeekValue: Int, coreDataMOContext: NSManagedObjectContext) {
        // init tableview
        tableView = UITableView(frame: CGRect(x: 0, y: 0, width: self.bounds.width, height: self.bounds.height), style: .plain)
        tableView.separatorStyle = .none
        tableView.delegate = self
        tableView.dataSource = self
        tableView.register(UITableViewCell.self, forCellReuseIdentifier: "WeekdayAppointmentCell")
        self.addSubview(tableView)
        
        // init calendar
        weekday = dayOfWeekValue
        date = currentDate
        let calendar = Calendar.current
        if calendar.component(.weekday, from: date) != weekday {
            date = dayOfWeek(weekday, fromDate: date)
        }
        
        // init coredata
        appointments = [AppointmentMO]()
        managedObjectContext = coreDataMOContext
        initializeFetchedResultsController()
        appointments = fetchedResultsController.fetchedObjects
    }
    
    // MARK: - CoreData
    func initializeFetchedResultsController() {
        
        // sort and predicate
        let request = NSFetchRequest<AppointmentMO>(entityName: "Appointment")
        let startDateSort = NSSortDescriptor(key: "startDate", ascending: true)
        request.sortDescriptors = [startDateSort]
        let dayStart = startOfDay(ofDate: date)
        let dayEnd = endOfDay(ofDate: date)
        let predicate = NSPredicate(format: "startDate >= %@ AND endDate <= %@", dayStart as NSDate, dayEnd as NSDate)
        request.predicate = predicate
        
        fetchedResultsController = NSFetchedResultsController(fetchRequest: request, managedObjectContext: managedObjectContext, sectionNameKeyPath: nil, cacheName: nil)
        fetchedResultsController.delegate = self
        
        do {
            try fetchedResultsController.performFetch()
        } catch {
            fatalError("Failed to initialize FetchedResultsController: \(error)")
        }
    }
    
    func updateFetchedAppointments() {
        do {
            try fetchedResultsController.performFetch()
        } catch {
            fatalError("Failed to initialize FetchedResultsController: \(error)")
        }
        tableView.reloadData()
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return fetchedResultsController.sections!.count
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        guard let sections = fetchedResultsController.sections else {
            fatalError("No sections in fetchedResultsController")
        }
        let sectionInfo = sections[section]
        return sectionInfo.numberOfObjects

    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell = tableView.dequeueReusableCell(withIdentifier: "WeekdayAppointmentCell", for: indexPath)
    
        // Set up the cell
        guard let appointment = self.fetchedResultsController?.object(at: indexPath) else {
            fatalError("Attempt to configure cell without a managed object")
        }
        
        cell = UITableViewCell(style: .subtitle, reuseIdentifier: cell.reuseIdentifier)
        cell.textLabel?.text = appointment.name
        cell.textLabel?.font = UIFont.systemFont(ofSize: 14)
        cell.detailTextLabel?.text = formatDate(appointment.startDate! as Date) + " - " + formatDate(appointment.endDate! as Date)
        cell.detailTextLabel?.font = UIFont.systemFont(ofSize: 11)
        cell.backgroundColor = #colorLiteral(red: 0.6894311442, green: 0.3841614216, blue: 0.001426193489, alpha: 0.5600920377)
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        // TODO: - Complete this
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
            fatalError("Unknown default in switch at WeekdayCollectionViewCell:controller:didChange:atSectionIndex")
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
            fatalError("Unknown default in switch at WeekdayCollectionViewCell:controller:didChange:indexPath")
        }
    }
    
    func controllerDidChangeContent(_ controller: NSFetchedResultsController<NSFetchRequestResult>) {
        tableView.endUpdates()
    }
    
    // MARK: - Calendar
    func formatDate(_ date: Date) -> String {
        return DateFormatter.localizedString(from: date, dateStyle: DateFormatter.Style.none, timeStyle: .short).components(separatedBy: " ")[0]
    }
    
    func startOfDay(ofDate date: Date) -> Date {
        var component = date.components
        component.shiftToStartOfDay()
        return Calendar.current.date(from: component)!
    }
    
    func endOfDay(ofDate date: Date) -> Date {
        var component = date.components
        component.shiftToEndOfDay()
        return Calendar.current.date(from: component)!
    }
    
    private func dayOfWeek(_ weekday: Int, fromDate date: Date) -> Date? {
        let sourceComponents = date.components
        var components = DateComponents()
        components.weekOfYear = sourceComponents.weekOfYear
        components.weekday = weekday
        components.yearForWeekOfYear = sourceComponents.yearForWeekOfYear
        return Calendar.current.date(from: components)
    }
    
}

// Res: https://stackoverflow.com/questions/28901586/swift-start-day-and-end-day-of-the-week-before-previous-week @ Jung Geon Choi
extension DateComponents {
    mutating func shiftToStartOfDay() {
        self.hour = 0
        self.minute = 0
        self.second = 0
    }
    
    mutating func shiftToEndOfDay(){
        self.hour = 23
        self.minute = 59
        self.second = 59
    }
    
    mutating func setWeekday(to weekdayValue: Int) {
        self.weekday = weekdayValue
    }
}
