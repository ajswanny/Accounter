//
//  PersistentContainer.swift
//  Accounts
//
//  Created by Alexander Swanson on 6/14/19.
//  Copyright © 2019 Oxygen. All rights reserved.
//

import UIKit
import CoreData

class DataController: NSObject {
    
    var managedObjectContext: NSManagedObjectContext!
    var persistentContainer: NSPersistentContainer!
    
    init(completionClosure: @escaping () -> ()) {
        
        persistentContainer = NSPersistentContainer(name: "DataModel")
        persistentContainer.loadPersistentStores() { (description, error) in
            if let error = error {
                fatalError("Failed to load Core Data stack: \(error)")
            }
            completionClosure()
        }
        managedObjectContext = persistentContainer.viewContext
        
    }
    
    func saveContext() {
        if managedObjectContext.hasChanges {
            do {
                try managedObjectContext.save()
            } catch {
                print("An error has occurred while saving: \(error)")
            }
        }
    }
    
    func loadClients() -> [ClientMO] {
        let request = ClientMO.createFetchRequest()
        do {
            return try managedObjectContext.fetch(request)
        } catch {
            fatalError("Could not load Clients from persisten store.")
        }
    }
    
}
