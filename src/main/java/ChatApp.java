import dto.Conversation;
import dto.Message;
import dto.Model;
import dto.Role;
import utils.Constant;

import java.util.Scanner;

public class ChatApp {

    public static void main(String[] args) {
        //TODO:
        // 1. Add {@link Scanner} for 'System.in'.
        // 2. Create instance of 'OpenAIClient' with model.
        // 3. Create Conversation.
        // 4. Get System prompt from console or use default -> Constant#DEFAULT_SYSTEM_PROMPT
        // and add to conversation messages. <br>
        // 5. Use `while` cycle, apply user message from console `scanner.nextLine()` and call openAiClient. <br>
        // 6. Apply 'Message' from openAiClient to conversation messages. <br>
        // 7. Don't forget to add 'exit' point from application via console.

        Scanner scanner = new Scanner(System.in); // 1.
        OpenAIClient client = new OpenAIClient(Model.GPT_4o_MINI, Constant.API_KEY); // 2.
        Conversation conversation = new Conversation(); // 3.

        // 4 ->
        System.out.println("Provide System prompt or tap 'enter' to continue.'");
        System.out.print("> ");
        String prompt = scanner.nextLine();
        if (!prompt.isBlank()) {
            conversation.addMessage(new Message(Role.SYSTEM, prompt));
            System.out.println("System prompt successfully added to conversation.");
        } else {
            conversation.addMessage(new Message(Role.SYSTEM, Constant.DEFAULT_SYSTEM_PROMPT));
            System.out.printf("No System prompt provided. Will be used default System prompt: '%s'%n", Constant.DEFAULT_SYSTEM_PROMPT);
        }
        System.out.println();
        // 4.

        // 5 ->
        System.out.println("Type your question or 'exit' to quit.");
        while (true) {
            System.out.print("> ");
            String userInput = scanner.nextLine();

            // 6 ->
            if ("exit".equalsIgnoreCase(userInput)) {
                System.out.println("Exiting the chat. Goodbye!");
                break;
            }
            // 6.

            conversation.addMessage(new Message(Role.USER, userInput));

            System.out.println("AI: ");
            try {
                Message aiMessage = client.streamResponseWithMessage(conversation.getMessages());
                conversation.addMessage(aiMessage);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }

            System.out.println();
        }
        // 5.

        scanner.close();
    }

}
