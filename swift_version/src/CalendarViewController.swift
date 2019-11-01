//
//  CalendarViewController.swift
//  Accounts
//
//  Created by Alexander Swanson on 7/3/19.
//  Copyright © 2019 Oxygen. All rights reserved.
//

import UIKit
import CoreData

class CalendarViewController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate, UICollectionViewDelegateFlowLayout, NSFetchedResultsControllerDelegate {

    var appDelegate = UIApplication.shared.delegate as! AppDelegate
    var fetchedResultsController: NSFetchedResultsController<AppointmentMO>!
    var appointments: [AppointmentMO]!
    var currentWeekAppointments: [Int: [AppointmentMO]]!
    var calendar: Calendar!
    var date: Date!
    var present = Date()
    var weekdayLabels: [UILabel]!
    var weekdayValues: [Int]!
    var currentDayHighlightingView: UIView!
    let currentMonthAndYearLabel = UILabel(frame: CGRect(x: 10, y: 0, width: 150, height: 40))
    var customTitleView: UIView {
        // Month and year label
        let now = Date()
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "LLLL yyyy"
        currentMonthAndYearLabel.text = dateFormatter.string(from: now)
        currentMonthAndYearLabel.font = UIFont.systemFont(ofSize: 20)
        return currentMonthAndYearLabel
    }
    
    @IBOutlet var collectionView: UICollectionView!
    @IBOutlet weak var sunLabel: UILabel!
    @IBOutlet weak var monLabel: UILabel!
    @IBOutlet weak var tueLabel: UILabel!
    @IBOutlet weak var wedLabel: UILabel!
    @IBOutlet weak var thuLabel: UILabel!
    @IBOutlet weak var friLabel: UILabel!
    @IBOutlet weak var satLabel: UILabel!
    @IBOutlet weak var weekdayNameLabelStackView: UIStackView!
    @IBOutlet weak var clientsOverlayButton: UIBarButtonItem!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        calendar = Calendar.current
        date = Date()
        weekdayLabels = [sunLabel, monLabel, tueLabel, wedLabel, thuLabel, friLabel, satLabel]
        weekdayValues = [Int](repeating: 0, count: 7)
        defineWeekdayValues(of: present)
        
        navigationController?.navigationBar.addSubview(customTitleView)
        weekdayNameLabelStackView.addBottomBorder(colored: UIColor.lightGray, ofSize: 0.5)
        
        let dayOfWeekValue = calendar.component(.weekday, from: present) - 1
        currentDayHighlightingView = weekdayLabels[dayOfWeekValue].bottomBorder(colored: UIColor.red, ofSize: 2)
        weekdayLabels[dayOfWeekValue].addSubview(currentDayHighlightingView)
        updateWeekdayLabelsWithNewDateValues()
        
        collectionView.delegate = self
        collectionView.dataSource = self
        toggleClientOverlayButton()
        
        initializeFetchedResultsController()
        currentWeekAppointments = fetchAppointments(inCurrentWeekOf: present)
        
        collectionView.register(WeekdayCollectionViewCell.self, forCellWithReuseIdentifier: "WeekdayCollectionViewCell")
    }
    
    // MARK: - Navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    
    override func viewWillTransition(to size: CGSize, with coordinator: UIViewControllerTransitionCoordinator) {
        super.viewWillTransition(to: size, with: coordinator)
        toggleClientOverlayButton()
    }
    
    // MARK: - CoreData
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
    
    // MARK: - UICollectionViewDelegate
    private func date(_ date: Date, isInCurrentWeekOf referencePoint: Date) -> Bool {
        let currentYearValue = calendar.component(.year, from: referencePoint)
        let currentWeekValue = calendar.component(.weekOfYear, from: referencePoint)
        let givenDateYearValue = calendar.component(.year, from: date)
        let givenDateWeekValue = calendar.component(.weekOfYear, from: date)
        if currentYearValue == givenDateYearValue && currentWeekValue == givenDateWeekValue {
            return true
        } else {
            return false
        }
    }
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of items
        return 7
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        var cell = collectionView.dequeueReusableCell(withReuseIdentifier: "WeekdayCollectionViewCell", for: indexPath) as! WeekdayCollectionViewCell
        cell.setData(currentDate: date, dayOfWeekValue: indexPath.row + 1, coreDataMOContext: appDelegate.managedObjectContext!)
        cell.updateFetchedAppointments()
        cell.addRightBorder(colored: UIColor.lightGray, ofSize: 0.5)
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: collectionView.bounds.width/7, height: collectionView.bounds.height)
    }
    
    /*
     rs: https://stackoverflow.com/questions/45391651/cell-size-not-updating-after-changing-flow-layouts-itemsize
    */
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        let boundsSize = CGSize(width: Int(view.bounds.width), height: Int(view.bounds.height))
        let flowLayout = collectionView.collectionViewLayout as! UICollectionViewFlowLayout
        if flowLayout.itemSize != boundsSize {
            flowLayout.itemSize = boundsSize
            flowLayout.invalidateLayout()
        }
    }
    
    // MARK: - Calendar Implementations
    private func defineWeekdayValues(of date: Date) {
        let firstDayOfCurrentWeek = calendar.component(.day, from: firstDayOfWeek(from: date)!)
        if calendar.component(.month, from: firstDayOfWeek(from: date)!) == calendar.component(.month, from: lastDayOfWeek(from: date)!) {
            weekdayValues[0] = firstDayOfCurrentWeek
            for i in 1...6 {
                weekdayValues[i] = weekdayValues[i-1] + 1
            }
        } else {
            let trailingMonthValue = calendar.component(.month, from: firstDayOfWeek(from: date)!)
            let lastDayOfTrailingMonth = lastDay(ofMonth: trailingMonthValue, inYear: calendar.component(.year, from: date))
            var trailingMonthDayValues = [Int]()
            for d in firstDayOfCurrentWeek...lastDayOfTrailingMonth {
                trailingMonthDayValues.append(d)
            }
            
            var leadingMonthDayValues = [Int]()
            for d in 1...7-trailingMonthDayValues.count {
                leadingMonthDayValues.append(d)
            }
            
            weekdayValues = trailingMonthDayValues + leadingMonthDayValues
        }
        updateWeekdayLabelsWithNewDateValues()
    }
    
    // 1 == forward, 0 == backward
    private func updateCurrentWeekInDisplay(inDirection direction: Int) {
        let reference = date!
        if direction == 0 {
            date = calendar.date(byAdding: .weekOfYear, value: -1, to: date)
        } else {
            date = calendar.date(byAdding: .weekOfYear, value: 1, to: date)
        }
        
        // the view indicating the present day of the week
        if calendar.component(.weekOfYear, from: date) == calendar.component(.weekOfYear, from: present) {
            currentDayHighlightingView.isHidden = false
        } else {
            currentDayHighlightingView.isHidden = true
        }
        
        // the views indicating the current month and year
        if calendar.component(.month, from: date) != calendar.component(.month, from: reference) {
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "LLLL"
            var values = currentMonthAndYearLabel.text!.components(separatedBy: " ")
            values[0] = dateFormatter.string(from: date)
            currentMonthAndYearLabel.text = values[0] + " " + values [1]
        }
        if calendar.component(.year, from: date) != calendar.component(.year, from: reference) {
            var values = currentMonthAndYearLabel.text!.components(separatedBy: " ")
            values[1] = "\(calendar.component(.year, from: date))"
            currentMonthAndYearLabel.text = values[0] + " " + values[1]
        }
        
        // update the weekday values (data and their respective views)
        defineWeekdayValues(of: date)
        
        currentWeekAppointments = fetchAppointments(inCurrentWeekOf: date)
        collectionView.reloadData()
    }
    
    private func revertCalendarViewToPresent() {
        currentDayHighlightingView.isHidden = false
        if calendar.component(.month, from: date) != calendar.component(.month, from: present) {
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "LLLL"
            var values = currentMonthAndYearLabel.text!.components(separatedBy: " ")
            values[0] = dateFormatter.string(from: present)
            currentMonthAndYearLabel.text = values[0] + " " + values [1]
        }
        if calendar.component(.year, from: date) != calendar.component(.year, from: present) {
            var values = currentMonthAndYearLabel.text!.components(separatedBy: " ")
            values[1] = "\(calendar.component(.year, from: present))"
            currentMonthAndYearLabel.text = values[0] + " " + values[1]
        }
        
        defineWeekdayValues(of: present)
        
        // reset date value for collection view data
        date = present
        collectionView.reloadSections(IndexSet(arrayLiteral: 0))
    }
    
    private func lastDay(ofMonth month: Int, inYear year: Int) -> Int {
        var components = DateComponents(calendar: calendar, year: year, month: month)
        components.setValue(month + 1, for: .month)
        components.setValue(0, for: .day)
        let date = calendar.date(from: components)!
        return calendar.component(.day, from: date)
    }
    
    private func firstDayOfWeek(from date: Date) -> Date? {
        let sourceComponents = date.components
        var components = DateComponents()
        components.weekOfYear = sourceComponents.weekOfYear
        components.weekday = 1
        components.yearForWeekOfYear = sourceComponents.yearForWeekOfYear
        return calendar.date(from: components)
    }
    
    private func lastDayOfWeek(from date: Date) -> Date? {
        let sourceComponents = date.components
        var components = DateComponents()
        components.weekOfYear = sourceComponents.weekOfYear
        components.weekday = 7
        components.yearForWeekOfYear = sourceComponents.yearForWeekOfYear
        return calendar.date(from: components)
    }
    
    private func dayOfWeek(_ weekday: Int, fromDate date: Date) -> Date? {
        let sourceComponents = date.components
        var components = DateComponents()
        components.weekOfYear = sourceComponents.weekOfYear
        components.weekday = weekday
        components.yearForWeekOfYear = sourceComponents.yearForWeekOfYear
        return calendar.date(from: components)
    }
    
    private func updateWeekdayLabelsWithNewDateValues() {
        func data(day: String, weekdayValue: Int) -> String {
            return "\(day)  \(weekdayValues[weekdayValue])"
        }
        for i in 0...6 {
            let weekdayLabel = weekdayLabels[i]
            switch i {
            case 0:
                weekdayLabel.text = data(day: "Sun", weekdayValue: i)
                break
            case 1:
                weekdayLabel.text = data(day: "Mon", weekdayValue: i)
                break
            case 2:
                weekdayLabel.text = data(day: "Tue", weekdayValue: i)
                break
            case 3:
                weekdayLabel.text = data(day: "Wed", weekdayValue: i)
                break
            case 4:
                weekdayLabel.text = data(day: "Thu", weekdayValue: i)
                break
            case 5:
                weekdayLabel.text = data(day: "Fri", weekdayValue: i)
                break
            case 6:
                weekdayLabel.text = data(day: "Sat", weekdayValue: i)
                break
            default:
                fatalError("Could not generate the weekday labels in CalendarViewController's data source")
            }
        }
    }
    
    private func toggleClientOverlayButton() {
        if UIDevice.current.orientation == .landscapeLeft || UIDevice.current.orientation == .landscapeRight {
            clientsOverlayButton.isEnabled = false
//            splitViewController?.
        } else {
            clientsOverlayButton.isEnabled = true
        }
    }
    
    private func fetchAppointments(inCurrentWeekOf referencePoint: Date) -> [Int: [AppointmentMO]] {
        var data = [AppointmentMO]()
        var sortedData = [Int: [AppointmentMO]]()
        if let objects = fetchedResultsController.fetchedObjects {
            appointments = objects
            for appointment in appointments {
                if date(appointment.startDate! as Date, isInCurrentWeekOf: referencePoint) {
                    data.append(appointment)
                }
            }
        
            for i in 0...6 {
                sortedData[i] = [AppointmentMO]()
                for appointment in data {
                    if calendar.component(.weekday, from: appointment.startDate! as Date) == i+1 {
                        sortedData[i]?.append(appointment)
                    }
                }
            }
        }
        return sortedData
    }
    
    // MARK: - Action connections
    @IBAction func userDidSwipeRight(_ sender: UISwipeGestureRecognizer) {
        updateCurrentWeekInDisplay(inDirection: 0)
    }
    
    @IBAction func userDidSwipeLeft(_ sender: UISwipeGestureRecognizer) {
        updateCurrentWeekInDisplay(inDirection: 1)
    }
    
    @IBAction func userDidSelectTodayTool(_ sender: UIBarButtonItem) {
        revertCalendarViewToPresent()
    }
    
    @IBAction func userDidTapClientsButton(_ sender: UIBarButtonItem) {
        UIView.animate(withDuration: 0.2, animations: {
            self.splitViewController?.preferredDisplayMode = .primaryOverlay
        })
    }
    
}

extension UIView {
    
    func addTopBorder(colored color: UIColor?, ofSize borderWidth: CGFloat) {
        let border = UIView()
        border.backgroundColor = color
        border.autoresizingMask = [.flexibleWidth, .flexibleBottomMargin]
        border.frame = CGRect(x: 0, y: 0, width: frame.size.width, height: borderWidth)
        addSubview(border)
    }
    
    func addBottomBorder(colored color: UIColor?, ofSize borderWidth: CGFloat) {
        let border = UIView()
        border.backgroundColor = color
        border.autoresizingMask = [.flexibleWidth, .flexibleTopMargin]
        border.frame = CGRect(x: 0, y: frame.size.height - borderWidth, width: frame.size.width, height: borderWidth)
        addSubview(border)
    }
    
    func addRightBorder(colored color: UIColor?, ofSize borderWidth: CGFloat) {
        let border = UIView()
        border.tag = 2
        border.backgroundColor = color
        border.autoresizingMask = [.flexibleHeight, .flexibleLeftMargin]
        border.frame = CGRect(x: frame.size.width - borderWidth, y: 0, width: borderWidth, height: frame.size.height)
        addSubview(border)
    }
    
    func bottomBorder(colored color: UIColor?, ofSize borderWidth: CGFloat) -> UIView {
        let border = UIView()
        border.backgroundColor = color
        border.autoresizingMask = [.flexibleWidth, .flexibleTopMargin]
        border.frame = CGRect(x: 0, y: frame.size.height - borderWidth, width: frame.size.width, height: borderWidth)
        return border
    }
    
    func rightBorder(colored color: UIColor?, ofSize borderWidth: CGFloat) -> UIView {
        let border = UIView()
        border.tag = 2
        border.backgroundColor = color
        border.autoresizingMask = [.flexibleHeight, .flexibleLeftMargin]
        border.frame = CGRect(x: frame.size.width - borderWidth, y: 0, width: borderWidth, height: frame.size.height)
        return border
    }
    
}

extension Date {
    var components: DateComponents {
        return Calendar.current.dateComponents(Set([.year, .month, .day, .hour, .minute, .second, .weekday, .weekOfYear, .yearForWeekOfYear]), from: self)
    }
}
