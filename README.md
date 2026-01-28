# Discord Analytics Bot

A Java-based Discord analytics bot that tracks user activity, message counts, and leaderboards for an 800+ member community.

## Features
- **Activity Tracking**: Monitors user messages and engagement in real-time
- **Leaderboards**: Displays top chatters with `!top` and `!top [number]` commands
- **User Statistics**: Shows individual stats with `!stats` command
- **Persistent Storage**: Uses SQLite database to store analytics data
- **Cloud Deployment**: Deployed on Railway for 24/7 uptime

## Technologies Used
- **Java**: Core programming language
- **JDA (Java Discord API)**: Discord bot framework
- **SQLite**: Lightweight database for data persistence
- **Railway**: Cloud hosting platform
- **Gradle**: Build automation tool

## Commands
- `!stats` - View your message count and activity
- `!top` - See the top 10 most active users
- `!top [number]` - View top N users (e.g., `!top 25`)

## Use Case
Built to manage and analyze engagement for a YouTube community Discord server with 800+ members and 32K+ YouTube subscribers.

## Setup
1. Clone the repository
2. Add your Discord bot token as an environment variable: `DISCORD_TOKEN`
3. Run with Gradle: `./gradlew run`

## Database Schema
SQLite database stores:
- User IDs
- Message counts
- Timestamps for activity tracking
