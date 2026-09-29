package budgeting.com.example.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatClientController {
    private final ChatClient chatClient;

    public ChatClientController(ChatClient chatClient){
        this.ChatClient = chatClient;
    }

    @GetMapping("/chat")
    public String chat(String prompt) {
        return this.ChatClient.prompt().user(prompt).call().content();
    }
}
