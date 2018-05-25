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
      "/events",
      "/mandataries",
      "/companies",
      "/nrn",
      "/request-types"
    ],
    "target": "http://localhost:8888",
    "secure": false,
    "logLevel": "debug"
  }
  ]

module.exports = PROXY_CONFIG;
