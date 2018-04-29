const PROXY_CONFIG = [
  {
    context: [
      "/about",
      "/citizens",
      "/requests",
      "/auth"
    ],
    "target": "http://localhost:8888",
    "secure": false,
    "logLevel": "debug"
  }
  ]

module.exports = PROXY_CONFIG;
