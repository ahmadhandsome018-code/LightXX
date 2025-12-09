LightX — Native Android (Java) SMS Forwarder (Ready for GitHub Actions)
=====================================================================

This repository contains a ready-to-build Android app (Java) that listens for incoming SMS
and forwards them to a configured email using Brevo (Sendinblue) SMTP API.

What is included:
- app/ (Android module with Java source)
- .github/workflows/android.yml (GitHub Actions workflow to build APK using Gradle action)

Important configuration (already embedded per your request):
- Brevo API key and target/sender emails are embedded in code:
  - API key: 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
  - Sender: alahlymasr9@gmail.com
  - Target: ainsports12@gmail.com

Usage:
1. Create a new GitHub repository called LightX and push these files (root contains 'app' and '.github').
2. On push to main, GitHub Actions will run the workflow and (if successful) produce an APK artifact.
3. Download the APK artifact and install on a real device (grant SMS permissions).

Legal & Security:
- Ensure explicit written consent from device owner. This app forwards SMS; misuse is illegal.
- For production, move API keys out of source code and use secrets in Actions or remote config.
