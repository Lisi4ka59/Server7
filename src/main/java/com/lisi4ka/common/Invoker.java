package com.lisi4ka.common;

import com.lisi4ka.commands.*;
import com.lisi4ka.utils.PackagedCommand;

import java.util.HashMap;
import java.util.List;

public class Invoker {

    public HashMap<String, Command> commands = new HashMap<>();
    public Invoker (List collection) {
        commands.put("help", new HelpCommand());
        commands.put("add", new AddCommand(collection));
        commands.put("info", new InfoCommand(collection));
        commands.put("show", new ShowCommand(collection));
        commands.put("clear", new ClearCommand(collection));
        commands.put("remove_first", new RemoveFirstCommand(collection));
        commands.put("remove_head", new RemoveHeadCommand(collection));
        commands.put("load", new LoadCommand(collection));
        commands.put("remove_by_id", new RemoveByIdCommand(collection));
        commands.put("update", new UpdateIdCommand(collection));
        commands.put("print_descending", new PrintDescendingCommand(collection));
        commands.put("print_unique_standard_of_living", new PrintUniqueStandardOfLivingCommand(collection));
        commands.put("print_field_ascending_standard_of_living", new PrintFieldAscendingStandardOfLivingCommand(collection));
        commands.put("add_if_min", new AddIfMinCommand(collection));
        commands.put("execute_script", new NullCommand());
        commands.put("exit", new NullCommand());
    }
    public String run(String commandText, String user) {
        try {
            String[] command =  commandText.split(" ");
            if (commands.containsKey(command[0])) {
                Command cmd = commands.get(command[0]);
                String result;
                if ("add".equals(command[0]) || "add_if_min".equals(command[0])){
                    result = cmd.execute(command[1], user);
                }else if("clear".equals(command[0])){
                    result = cmd.execute("", user);
                }else {
                    if (command.length == 1) {

                        result = cmd.execute();
                        //System.out.println(result);
                    } else {
                        result = cmd.execute(command[1]);
                        //System.out.println(result);
                    }
                }
                return result;
            }
            else {
                    return String.format("Unknown command \"%s\". Type \"help\" to see list of commands", commandText);
            }
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace(System.out);
            return String.format("Error while execution command \"%s\"\n", commandText);
        }
    }
}
