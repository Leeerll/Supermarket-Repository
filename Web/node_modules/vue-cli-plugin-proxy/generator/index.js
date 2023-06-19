module.exports = (api, options) => {
  const fs = require('fs')

  const files = {}
  if (!fs.existsSync(api.resolve('proxy.config.js'))) {
    files['proxy.config.js'] = './templates/proxy.config.js'
  }
  if (!fs.existsSync(api.resolve('proxy.config.local.js'))) {
    files['proxy.config.local.js'] = './templates/proxy.config.js'
  }
  api.render(files, options)

  api.onCreateComplete(() => {
    const gitignorePath = api.resolve('.gitignore')
    let content

    if (fs.existsSync(gitignorePath)) {
      content = fs.readFileSync(gitignorePath, {encoding: 'utf8'})
    } else {
      content = ''
    }

    if (content.indexOf('proxy.config.local.js') === -1) {
      content += '\n\n# Local proxy config (vue-cli-plugin-proxy)\nproxy.config.local.js'

      fs.writeFileSync(gitignorePath, content, {encoding: 'utf8'})
      api.exitLog('Added proxy.config.local.js to gitignore', 'info')
    }
  })
};