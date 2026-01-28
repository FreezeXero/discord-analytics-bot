import com.rafay.bot.BotListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    public static void main(String[] args) throws Exception {
        String token = System.getenv("DISCORD_TOKEN"); // matches your setx name
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("DISCORD_TOKEN env var not set");
        }

        JDA jda = JDABuilder.createDefault(token,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_MEMBERS // optional
                )
                .addEventListeners(new BotListener())
                .build();

        jda.awaitReady();
        System.out.println("Bot connected!");
    }
}



