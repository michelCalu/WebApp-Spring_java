const PROXY_CONFIG = [
  {
    context: [
      "/about",
      "/createpeople",
      "/showpeople"
    ],
    "target": "http://localhost:8080",
    "secure": false,
    "logLevel": "debug"
  }
  ]

module.exports = PROXY_CONFIG;
