/**
 * 公共JavaScript函数库
 * 包含认证、工具函数等公共逻辑
 */

// HTML转义函数，防止XSS
function escapeHtml(text) {
    if (!text) return '';
    const map = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#039;'
    };
    return text.replace(/[&<>"']/g, function(m) { return map[m]; });
}

// 节流函数
function throttle(func, limit) {
    let inThrottle;
    return function() {
        const args = arguments;
        const context = this;
        if (!inThrottle) {
            func.apply(context, args);
            inThrottle = true;
            setTimeout(() => inThrottle = false, limit);
        }
    }
}

// 防抖函数
function debounce(func, delay) {
    let timer;
    return function() {
        const args = arguments;
        const context = this;
        clearTimeout(timer);
        timer = setTimeout(() => func.apply(context, args), delay);
    };
}

// Token管理
const TokenManager = {
    // 获取Token
    getToken() {
        return localStorage.getItem('jwt_token');
    },
    
    // 设置Token
    setToken(token) {
        localStorage.setItem('jwt_token', token);
    },
    
    // 清除Token
    clearToken() {
        localStorage.removeItem('jwt_token');
    },
    
    // 检查Token是否存在
    hasToken() {
        return !!this.getToken();
    }
};

// 通用的登录检查函数
async function checkLogin() {
    try {
        const token = TokenManager.getToken();
        if (!token) return null;
        
        const response = await fetch('/api/user/info', { 
            headers: { 'Authorization': 'Bearer ' + token } 
        });
        
        if (response.status === 401) {
            // Token过期，清理并重定向
            TokenManager.clearToken();
            console.log('Token已过期，已自动清理');
            return null;
        }
        
        const result = await response.json();
        if (result.code === 200 && result.data) {
            return result.data;
        }
        return null;
    } catch (e) { 
        console.log("身份验证失效");
        TokenManager.clearToken();
        return null;
    }
}

// 通用的登出函数
function logout() {
    if(confirm("确定要断开终端连接吗？")) {
        TokenManager.clearToken();
        window.location.reload();
    }
}

// 通用的用户栏更新函数
function updateUserBar(user) {
    const userBar = document.getElementById('userBar');
    if (!userBar) return;
    
    if (!user) {
        userBar.innerHTML = '<a href="login.html" style="color: var(--primary-color); text-decoration: none; font-weight: bold;">🔑 漂泊者登录</a>';
        return;
    }
    
    // 使用DOM操作而不是innerHTML，防止XSS
    userBar.innerHTML = '';
    
    const statusSpan = document.createElement('span');
    statusSpan.innerHTML = '✓ 已接入：<strong>' + escapeHtml(user.nickname) + '</strong>';
    userBar.appendChild(statusSpan);
    
    if (user.role === 1) {
        const adminLink = document.createElement('a');
        adminLink.href = 'admin.html';
        adminLink.className = 'admin-btn';
        adminLink.textContent = '🛡️ 核心控制台';
        userBar.appendChild(adminLink);
    }
    
    const logoutLink = document.createElement('a');
    logoutLink.href = 'javascript:void(0)';
    logoutLink.onclick = logout;
    logoutLink.style.cssText = 'color:#e74c3c; text-decoration:none; cursor:pointer;';
    logoutLink.textContent = '[断开]';
    userBar.appendChild(logoutLink);
}

// 通用的Toast通知函数
function showToast(message, type = 'info', duration = 3000) {
    const toast = document.createElement('div');
    toast.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 20px;
        border-radius: 10px;
        backdrop-filter: blur(10px);
        z-index: 1000;
        animation: slideIn 0.3s ease-out;
        border-left: 4px solid ${type === 'success' ? '#27ae60' : type === 'error' ? '#e74c3c' : '#f39c12'};
        background: ${type === 'success' ? 'rgba(39, 174, 96, 0.9)' : type === 'error' ? 'rgba(231, 76, 60, 0.9)' : 'rgba(243, 156, 18, 0.9)'};
        color: white;
        max-width: 300px;
        word-wrap: break-word;
    `;
    toast.textContent = message;
    document.body.appendChild(toast);

    setTimeout(() => {
        toast.style.animation = 'slideOut 0.3s ease-out';
        setTimeout(() => toast.remove(), 300);
    }, duration);
}

// 添加Toast动画样式
if (!document.getElementById('toast-styles')) {
    const style = document.createElement('style');
    style.id = 'toast-styles';
    style.textContent = `
        @keyframes slideIn {
            from {
                transform: translateX(400px);
                opacity: 0;
            }
            to {
                transform: translateX(0);
                opacity: 1;
            }
        }
        @keyframes slideOut {
            from {
                transform: translateX(0);
                opacity: 1;
            }
            to {
                transform: translateX(400px);
                opacity: 0;
            }
        }
    `;
    document.head.appendChild(style);
}

// 通用的API请求函数
async function apiRequest(url, options = {}) {
    const token = TokenManager.getToken();
    const defaultOptions = {
        headers: {
            'Content-Type': 'application/json',
            ...(token && { 'Authorization': 'Bearer ' + token })
        }
    };
    
    const mergedOptions = {
        ...defaultOptions,
        ...options,
        headers: {
            ...defaultOptions.headers,
            ...options.headers
        }
    };
    
    try {
        const response = await fetch(url, mergedOptions);
        
        if (response.status === 401) {
            TokenManager.clearToken();
            showToast('登录已过期，请重新登录', 'error');
            setTimeout(() => window.location.href = 'login.html', 1500);
            return null;
        }
        
        return await response.json();
    } catch (error) {
        console.error('API请求失败:', error);
        showToast('网络请求失败，请检查连接', 'error');
        return null;
    }
}
