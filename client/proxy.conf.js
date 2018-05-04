const PROXY_CONFIG = [
  {
    context: [
      "/about",
      "/citizens",
      "/requests",
      "/employees",
      "/auth"
    ],
    "target": "http://localhost:8888/web-1.0-SNAPSHOT/",
    "secure": false,
    "logLevel": "debug"
  }
  ]

module.exports = PROXY_CONFIG;
