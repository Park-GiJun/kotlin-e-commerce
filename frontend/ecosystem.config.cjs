module.exports = {
    apps: [
        {
            name: 'frontend', // PM2에서 사용할 애플리케이션 이름
            script: 'node_modules/vite/bin/vite.js', // Vite 실행 경로
            args: 'preview --host 0.0.0.0 --port 8080', // 외부 접근 허용
            env: {
                NODE_ENV: 'development'
            },
            env_production: {
                NODE_ENV: 'production'
            },
            log_date_format: 'YYYY-MM-DD HH:mm:ss', // 로그에 날짜와 시간 추가
            output: './logs/out.log', // 표준 출력 로그 파일
            error: './logs/error.log', // 표준 에러 로그 파일
            combine_logs: true, // 여러 인스턴스의 로그를 하나로 결합
            instances: 1, // 실행 인스턴스 수
            autorestart: true, // 오류 발생 시 자동 재시작
            watch: false, // 파일 변경 감시 비활성화
            max_memory_restart: '300M' // 메모리 제한 초과 시 재시작
        }
    ]
};
