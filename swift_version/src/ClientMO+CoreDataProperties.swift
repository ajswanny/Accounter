//
//  ClientMO+CoreDataProperties.swift
//  Accounts
//
//  Created by Alexander Swanson on 6/24/19.
//  Copyright © 2019 Oxygen. All rights reserved.
//
//

import Foundation
import CoreData


extension ClientMO {

    @nonobjc public class func createFetchRequest() -> NSFetchRequest<ClientMO> {
        return NSFetchRequest<ClientMO>(entityName: "Client")
    }
    
    public func getFullName() -> String {
        return firstName + " " + (lastName ?? "")
    }

    @NSManaged public var firstName: String
    @NSManaged public var lastName: String?
    @NSManaged public var emailAddress: String?
    @NSManaged public var notes: String?
    @NSManaged public var phoneNumber: String?
    @NSManaged public var orderIndex: String
    @NSManaged public var address: AddressMO?
    @NSManaged public var appointments: NSSet?

}

// MARK: Generated accessors for appointments
extension ClientMO {

    @objc(addAppointmentsObject:)
    @NSManaged public func addToAppointments(_ value: AppointmentMO)

    @objc(removeAppointmentsObject:)
    @NSManaged public func removeFromAppointments(_ value: AppointmentMO)

    @objc(addAppointments:)
    @NSManaged public func addToAppointments(_ values: NSSet)

    @objc(removeAppointments:)
    @NSManaged public func removeFromAppointments(_ values: NSSet)

}
