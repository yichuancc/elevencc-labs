const proxyObj = {};

proxyObj['/'] = {
  // websocket
  ws: false,
  target: 'http://localhost:7200',
  // 发送请求头host会被设置成target
  changeOrigin: true,

  // 不重写请求地址
  pathRewrite: {
    '^/upload': '/oss/file'
  }
};

module.exports = {
  devServer: {
    host: 'localhost',
    port: '8080',
    proxy: proxyObj
  }
};
