module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8081', // 后端的地址
        changeOrigin: true,  // 设置为 true 解决跨域问题
        secure: false,       // 如果后端是 HTTP，设置为 false
        pathRewrite: {
          '^/api': ''         // 去掉请求路径中的 '/api' 前缀
        }
      },
    },
  },
};
