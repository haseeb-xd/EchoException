<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# EchoException Changelog

## [Unreleased]

### Added
- Nothing yet!

---

## [1.0.0] - 2025-10-XX

### Added
- Initial public release of EchoException.
- Exception detection from Run/Debug console via `ExceptionConsoleListener`.
- Voice announcement system driven by `VoicePack` and `SoundTriggerService`.
- Visual toast notifications for detected exceptions via `NotificationService` and the `EchoException Notifications` group.
- Settings page under Settings → Tools → Echo Exception with:
  - Voice pack selection
  - Volume control
  - Enable/disable notifications and sounds
- Achievement tracking of encountered exceptions via `AchievementManager`.
- Startup activity wiring via `EchoExceptionStartupActivity` and service registration in `plugin.xml`.
- Project scaffolding based on the IntelliJ Platform Plugin Template and Gradle build tasks to run the IDE.

