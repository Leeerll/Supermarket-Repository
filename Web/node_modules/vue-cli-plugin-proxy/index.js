module.exports = api => {
  const fs = require('fs')

  let proxy = {}
  if (fs.existsSync(api.resolve('proxy.config.js'))) {
    proxy = require(api.resolve('proxy.config.js'))
  }
  if (fs.existsSync(api.resolve('proxy.config.local.js'))) {
    proxy = Object.assign(proxy, require(api.resolve('proxy.config.local.js')))
  }

  if (proxy != null) {
    api.chainWebpack(config => {
      config
        .devServer
        .proxy(proxy)
    })
  }
}

module.exports.defaultModes = {
  serve: 'development'
}
