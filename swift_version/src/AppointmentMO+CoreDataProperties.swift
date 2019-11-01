//
//  AppointmentMO+CoreDataProperties.swift
//  Accounts
//
//  Created by Alexander Swanson on 7/6/19.
//  Copyright © 2019 Oxygen. All rights reserved.
//
//

import Foundation
import CoreData


extension AppointmentMO {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<AppointmentMO> {
        return NSFetchRequest<AppointmentMO>(entityName: "Appointment")
    }

    @NSManaged public var name: String
    @NSManaged public var location: String?
    @NSManaged public var startDate: NSDate?
    @NSManaged public var endDate: NSDate?
    @NSManaged public var dayOfWeekValue: Int16
    @NSManaged public var attendees: NSSet?

}

// MARK: Generated accessors for attendees
extension AppointmentMO {

    @objc(addAttendeesObject:)
    @NSManaged public func addToAttendees(_ value: ClientMO)

    @objc(removeAttendeesObject:)
    @NSManaged public func removeFromAttendees(_ value: ClientMO)

    @objc(addAttendees:)
    @NSManaged public func addToAttendees(_ values: NSSet)

    @objc(removeAttendees:)
    @NSManaged public func removeFromAttendees(_ values: NSSet)

}
