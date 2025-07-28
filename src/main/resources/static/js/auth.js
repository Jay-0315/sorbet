// HttpOnly 쿠키 기반 인증 관리 클래스
class HttpOnlyAuthManager {
    constructor() {
        this.isAuthenticated = false;
    }

    // 로그인 함수 (HttpOnly 쿠키는 자동으로 전송됨)
    async login(userId, password) {
        try {
            const response = await fetch('/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ userId, password }),
                credentials: 'include' // 쿠키 포함
            });

            if (response.ok) {
                this.isAuthenticated = true;
                return { success: true, message: '로그인 성공' };
            } else {
                const errorData = await response.json();
                return { success: false, message: errorData.error || '로그인 실패' };
            }
        } catch (error) {
            console.error('로그인 오류:', error);
            return { success: false, message: '네트워크 오류' };
        }
    }

    // 로그아웃 함수
    async logout() {
        try {
            const response = await fetch('/logout', {
                method: 'POST',
                credentials: 'include' // 쿠키 포함
            });
            
            this.isAuthenticated = false;
            return { success: true, message: '로그아웃 성공' };
        } catch (error) {
            console.error('로그아웃 오류:', error);
            this.isAuthenticated = false;
            return { success: false, message: '로그아웃 중 오류 발생' };
        }
    }

    // 인증된 API 요청 헬퍼 함수
    async authenticatedRequest(url, options = {}) {
        const response = await fetch(url, {
            ...options,
            credentials: 'include', // HttpOnly 쿠키 자동 포함
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            }
        });

        if (response.status === 401) {
            // 토큰 만료 시 로그인 페이지로 리다이렉트
            this.isAuthenticated = false;
            window.location.href = '/login?error=expired';
            return null;
        }

        return response;
    }

    // 인증 상태 확인
    checkAuthStatus() {
        return this.isAuthenticated;
    }

    // 페이지 로드 시 인증 상태 확인
    async checkAuth() {
        try {
            const response = await fetch('/mypage', {
                method: 'GET',
                credentials: 'include'
            });
            
            this.isAuthenticated = response.ok;
            return this.isAuthenticated;
        } catch (error) {
            this.isAuthenticated = false;
            return false;
        }
    }
}

// 전역 인스턴스 생성
const httpOnlyAuthManager = new HttpOnlyAuthManager();

// 페이지 로드 시 인증 상태 확인
document.addEventListener('DOMContentLoaded', () => {
    httpOnlyAuthManager.checkAuth();
});

// 사용 예시:
/*
// 1. 로그인
httpOnlyAuthManager.login('user123', 'password123').then(result => {
    if (result.success) {
        console.log('로그인 성공');
        window.location.href = '/home';
    } else {
        console.error('로그인 실패:', result.message);
    }
});

// 2. 로그아웃
httpOnlyAuthManager.logout().then(result => {
    if (result.success) {
        console.log('로그아웃 성공');
        window.location.href = '/login';
    }
});

// 3. 인증된 API 요청
httpOnlyAuthManager.authenticatedRequest('/api/posts', {
    method: 'POST',
    body: JSON.stringify({ title: '제목', content: '내용' })
}).then(response => {
    if (response) {
        return response.json();
    }
});
*/ 