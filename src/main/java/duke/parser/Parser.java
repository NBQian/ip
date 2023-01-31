package duke.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import duke.command.AddCommand;
import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.ListCommand;
import duke.command.MarkCommand;
import duke.command.UnmarkCommand;
import duke.commandtype.CommandType;
import duke.dukeexception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Todo;


public class Parser {
    public static Command parse(String inputLine) throws DukeException {
        try {
            DukeException.checkInput(inputLine);

            String[] words = inputLine.split(" ");

            CommandType commandtype = CommandType.valueOf(words[0].toUpperCase());

            switch (commandtype) {
            case BYE:
                return new ByeCommand();
            case LIST:
                return new ListCommand();
            case MARK:
                int taskNoMark = Integer.parseInt(words[1]);
                return new MarkCommand(taskNoMark);
            case UNMARK:
                int taskNoUnmark = Integer.parseInt(words[1]);
                return new UnmarkCommand(taskNoUnmark);
            case DEADLINE:
                String[] parts = inputLine.split("/");
                Deadline task = new Deadline(parts[0].split(" ", 2)[1], 0, parts[1]);
                return new AddCommand(task);
            case EVENT:
                String[] parts1 = inputLine.split(" /");
                Event event = new Event(parts1[0].split(" ", 2)[1], 0, parts1[1], parts1[2]);
                return new AddCommand(event);
            case TODO:
                Todo todo = new Todo(inputLine.split(" ", 2)[1], 0);
                return new AddCommand(todo);
            case DELETE:
                return new DeleteCommand(Integer.parseInt(words[1]));
            default:
                throw new DukeException("Not a valid command: " + inputLine);
            }
        } catch (DukeException e) {
            throw e;
        }
    }

    public static LocalDateTime formatDateTime(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
        LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
        return dateTime;
    }

    public static String reverseFormatDateTime(LocalDateTime input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
        return input.format(formatter);
    }

    public static String transformDateTime(LocalDateTime dateTime) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy 'at' HH:mm");
        return dateTime.format(outputFormatter);
    }
}
