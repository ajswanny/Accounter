/*
alexanderjswanson@icloud.com | https://github.com/ajswanny

References:
    Design Patterns - Gama et. al
*/


/* Package */
package Accounter.java.customs.command;


import Accounter.java.client.Client;

/**
 *
 */
public class DeleteClientCommand extends Command<Client>{

   public DeleteClientCommand() {


   }

   @Override
   public void execute() {


   }

   @Override
   public void un_execute() {

       this.object = memento.get_state();

   }

}
