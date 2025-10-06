<div align="center">

![echo exception (300 x 300 px)](https://github.com/user-attachments/assets/e98a42e9-b928-4e8b-a450-8c362d616b86)

  <h1>Echo Exception</h1>
  <p><strong>Debugging has never sounded this cool.</strong></p>
  <p>
    <a href="https://github.com/haseeb-xd/EchoException/actions/workflows/build.yml">
      <img src="https://github.com/haseeb-xd/EchoException/workflows/Build/badge.svg" alt="Build Status"/>
    </a>
    <a href="https://plugins.jetbrains.com/plugin/MARKETPLACE_ID">
      <img src="https://img.shields.io/jetbrains/plugin/v/MARKETPLACE_ID.svg" alt="Plugin Version"/>
    </a>
    <a href="https://plugins.jetbrains.com/plugin/MARKETPLACE_ID">
      <img src="https://img.shields.io/jetbrains/plugin/d/MARKETPLACE_ID.svg" alt="Plugin Downloads"/>
    </a>
  </p>

  [Features](#-features) • [Installation](#-installation) • [Settings](#%EF%B8%8F-settings) • [Contributing](#-contributing)

</div>

---

## What's This About?

Your `NullPointerException` just became a main character moment.

Echo Exception is an IntelliJ plugin that announces your exceptions like a fighting game character entering the arena. When your code crashes, **Satoru Gojo** might casually remind you about that null pointer, or **Sage** from Valorant will precisely call out your array bounds violation. 

It's debugging, but make it cinematic.

---

<!-- Plugin description -->
Echo Exception transforms mundane stack traces into memorable moments. Instead of silently reading error messages, hear them announced by iconic characters with custom voice packs. Visual notifications pop up in sync with the audio, making exception handling feel less like a chore and more like an event.

Currently featuring voice packs from Satoru Gojo (Jujutsu Kaisen) and Sage (Valorant), with more characters coming soon. Perfect for developers who want personality in their debugging workflow, live coders, streamers, or anyone who's tired of silent exceptions.

**Key Features:**
- Audible exception announcements with character voices
- Animated toast notifications that appear on exception detection
- Multiple voice packs: Gojo's chaotic confidence and Sage's precise callouts
- Fully configurable settings for volume, notifications, and voice selection
<!-- Plugin description end -->

---

## Features

### Character Voice Packs

**Satoru Gojo** (Jujutsu Kaisen)  
Calm, confident, and slightly unhinged. Your exceptions never sounded this powerful.

**Sage** (Valorant)  
Precise, composed, and tactical. Every exception callout hits with purpose.

More characters incoming. Suggest your favorites in the [issues](https://github.com/haseeb-xd/EchoException/issues).

### Visual Toast Notifications

Clean, theme-aware popups appear in the bottom-right corner when exceptions strike:
- Shows exception type and custom voice pack messages

### Achievement System

Track your debugging journey with built-in achievements:
- View all unlocked achievements with progress bars
- See total exceptions encountered across all sessions
- Monitor milestone progress for each exception type
- Celebrate debugging victories with visual feedback

### Full Configuration Control

Access settings at **Settings → Tools → Echo Exception**:
- Switch between voice packs on the fly
- Adjust volume from 0-100%
- Enable/disable sounds independently from notifications

---

## Installation

### From IDE Marketplace (Easiest)
1. Open **Settings/Preferences → Plugins → Marketplace**
2. Search for **"Echo Exception"**
3. Click **Install** and restart

### From JetBrains Website
Visit [EchoException on JetBrains Marketplace](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID) and click **Install to IDE**.

### Manual Installation
1. Download the [latest release](https://github.com/haseeb-xd/EchoException/releases/latest)
2. **Settings/Preferences → Plugins → ⚙️ → Install Plugin from Disk...**
3. Select the `.zip` file
4. Restart your IDE

---

## Quick Start

After installation:

1. Write some deliberately broken code:
```java
String test = null;
test.length(); // Let the games begin
```

2. Run it and enjoy the show

3. Head to **Settings → Tools → Echo Exception** to customize

That's it. Your exceptions now have personality.

---

## Settings

Configure everything at **Settings → Tools → Echo Exception**

**Sound Tab**
- Voice pack selection
- Volume slider with real-time preview
- Enable/disable notification popups

**Achievements Tab**
- View all unlocked achievements
- Track progress toward milestones
- See total exceptions encountered
- Monitor stats per exception type
- Celebrate debugging victories

---

## Development

Built on the [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template).

### Build & Run Locally

```bash
git clone https://github.com/haseeb-xd/EchoException.git
cd EchoException
./gradlew buildPlugin
./gradlew runIde
```


### Memory Issues?

Edit `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx2g -XX:MaxMetaspaceSize=512m
```

---

## Screenshots

| Exception Announcement | Settings Panel |
|----------------------|----------------|
| ![Echo Preview](docs/preview_echo.gif) | ![Settings Preview](docs/preview_settings.png) |


---

## Contributing

Got ideas? Found bugs? Want to add voice packs?

- Open an [issue](https://github.com/haseeb-xd/EchoException/issues) for bugs or feature requests
- Submit a [pull request](https://github.com/haseeb-xd/EchoException/pulls) for code contributions
- Suggest new characters for voice packs
- Share it with fellow developers who need more drama in their debugging

All contributions welcome, no matter how small.




---


## License

Licensed under [Apache 2.0](LICENSE).

---

## Credits
**Special Thanks:**
- Voice samples from Jujutsu Kaisen and Valorant - [Maiaa](https://fakeyou.com/profile/maiaa/weights?page_size=24)
- Every developer who's ever screamed at a NullPointerException

---

## Support

Found this useful? 

- Star the repo
- Rate it on [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)
- Share it with your team
- Suggest new voice packs

Have questions? Open an [issue](https://github.com/haseeb-xd/EchoException/issues).

---

<div align="center">
  <sub>Built with actual debugging pain and a sense of humor 💜.</sub><br>
  <sub>"Because even exceptions deserve a dramatic entrance."</sub>
</div>
