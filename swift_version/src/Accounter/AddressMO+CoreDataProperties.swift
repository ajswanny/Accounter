//
//  AddressMO+CoreDataProperties.swift
//  Accounts
//
//  Created by Alexander Swanson on 6/23/19.
//  Copyright © 2019 Oxygen. All rights reserved.
//
//

import Foundation
import CoreData


extension AddressMO {

    @nonobjc public class func createFetchRequest() -> NSFetchRequest<AddressMO> {
        return NSFetchRequest<AddressMO>(entityName: "Address")
    }

    @NSManaged public var street: String?
    @NSManaged public var state: String?
    @NSManaged public var city: String?
    @NSManaged public var postalCode: String?
    @NSManaged public var respectiveClient: ClientMO?

}
