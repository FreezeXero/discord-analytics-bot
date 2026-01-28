package com.rafay.bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class BotListener extends ListenerAdapter {

    private final AnalyticsRepository repo = new AnalyticsRepository();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;

        String userId = event.getAuthor().getId();
        repo.incrementMessageCount(userId);

        String content = event.getMessage().getContentRaw().trim();

        // !stats (your personal count)
        if (content.equalsIgnoreCase("!stats")) {
            int count = repo.getMessageCount(userId);
            event.getChannel()
                    .sendMessage("You have sent **" + count + "** messages!")
                    .queue();
            return;
        }

        // !top or !top 25
        if (content.toLowerCase().startsWith("!top")) {
            int limit = 10;

            String[] parts = content.split("\\s+");
            if (parts.length >= 2) {
                try {
                    limit = Integer.parseInt(parts[1]);
                } catch (NumberFormatException ignored) {
                    // keep default
                }
            }

            List<AnalyticsRepository.UserCount> top = repo.getTopUsers(limit);

            if (top.isEmpty()) {
                event.getChannel().sendMessage("No data yet — start chatting 😈").queue();
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("🏆 **Top ").append(top.size()).append(" Chatters**\n");

            int rank = 1;
            for (var uc : top) {
                sb.append("**").append(rank).append(".** ")
                        .append("<@").append(uc.userId).append(">")
                        .append(" — ").append(uc.count).append(" messages\n");
                rank++;
            }

            event.getChannel().sendMessage(sb.toString()).queue();
        }
    }
}
