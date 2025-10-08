<div align="center">

![echo exception (300 x 300 px)](https://github.com/user-attachments/assets/e98a42e9-b928-4e8b-a450-8c362d616b86)

  <h1>Echo Exception</h1>
  <p><strong>Debugging has never sounded this cool.</strong></p>
  <p>
    <a href="https://github.com/haseeb-xd/EchoException/actions/workflows/build.yml">
      <img src="https://github.com/haseeb-xd/EchoException/workflows/Build/badge.svg" alt="Build Status"/>
    </a>
    <a href="https://plugins.jetbrains.com/plugin/28655-echo-exception">
      <img src="https://img.shields.io/jetbrains/plugin/v/MARKETPLACE_ID.svg" alt="Plugin Version"/>
    </a>
    <a href="https://plugins.jetbrains.com/plugin/28655-echo-exception">
      <img src="https://img.shields.io/jetbrains/plugin/d/MARKETPLACE_ID.svg" alt="Plugin Downloads"/>
    </a>
  </p>


</div>

---

<!-- Plugin description -->
<h2> Your Exceptions Have Never Sounded This Epic!</h2>

<p>Transform boring stack traces into legendary moments. When your code throws an exception, hear it announced by iconic characters with epic voice packs and notifications.</p>

<h3>✨ Features:</h3>
<ul>
    <li>🔊 <b>Epic Voice Announcements</b> - Hear exceptions called out by Satoru Gojo or Sage from Valorant</li>
    <li>🔔 <b>Notifications</b> - Visual popups appear in sync with audio announcements</li>
    <li>🏆 <b>Achievement System</b> - Track your debugging journey and unlock milestones</li>
    <li>🎚️ <b>Voice Pack Selection</b> - Choose between Gojo's chaotic confidence or Sage's tactical precision</li>
    <li>⚙️ <b>Full Customization</b> - Adjust volume, enable/disable sounds and notifications independently</li>
    <li>🎯 <b>Smart Detection</b> - Automatically catches exceptions from Run/Debug console</li>
    <li>🌈 <b>Exception Stats</b> - View total exceptions encountered and progress per type</li>
</ul>

<h3>🎭 Available Voice Packs:</h3>
<ul>
    <li><b>Satoru Gojo</b> (Jujutsu Kaisen) - Calm, confident, and perfectly dramatic</li>
    <li><b>Sage</b> (Valorant) - Sharp, tactical, and precisely timed callouts</li>
    <li>More characters coming soon! </li>
</ul>

<h3>🏆 Achievement System:</h3>
<p>Track your debugging milestones:</p>
<ul>
    <li>First exception caught</li>
    <li>10+ NullPointerExceptions</li>
    <li>View total exceptions and progress in Settings</li>
</ul>

<h3>💡 Perfect For:</h3>
<ul>
    <li>Developers who miss exceptions in console spam</li>
    <li>Live coders and streamers wanting entertaining debugging</li>
    <li>Teams looking to add personality to their workflow</li>
    <li>Anyone tired of silent exceptions</li>
</ul>

<h3>🛠️ Works With:</h3>
<p>All JVM languages: Java, Kotlin, Scala, Groovy, and more. Compatible with all IntelliJ Platform-based IDEs.</p>

<p><b>Configure at:</b> Settings → Tools → Echo Exception</p>
<!-- Plugin description end -->

---

## Installation

### From IDE Marketplace (Easiest)
1. Open **Settings/Preferences → Plugins → Marketplace**
2. Search for **"Echo Exception"**
3. Click **Install** and restart

### From JetBrains Website
Visit [EchoException on JetBrains Marketplace](https://plugins.jetbrains.com/plugin/28655-echo-exception) and click **Install to IDE**.

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
