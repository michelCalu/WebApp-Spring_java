const PROXY_CONFIG = [
  {
    context: [
      "/about",
      "/citizens",
      "/documents",
      "/requests",
      "/employees",
      "/auth",
      "/municipalities",
      "/departments",
      "/events"
    ],
    "target": "http://localhost:8888/web-1.0-SNAPSHOT/",
    "secure": false,
    "logLevel": "debug"
  }
  ]

module.exports = PROXY_CONFIG;
