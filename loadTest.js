import http from 'k6/http';
import { check, sleep } from 'k6';
import { Counter } from 'k6/metrics';
import encoding from 'k6/encoding';

const username = 'user';
const password = '123';

const credentials = `${username}:${password}`;
const encodedCredentials = encoding.b64encode(credentials);

export let errorRate = new Counter('errors');

export let options = {
    stages: [
        { duration: '1m', target: 500 },  // 500 users for 1 minute
        { duration: '1m', target: 1000 }, // 1000 users for 1 minute
        { duration: '1m', target: 5000 }, // 5000 users for 1 minute
        { duration: '1m', target: 10000 }, // 10000 users for 1 minute
    ],
    thresholds: {
        errors: ['count<100'],
        http_req_duration: ['p(95)<500'],
    },
};

export default function () {
    let res = http.get('http://localhost:8081/datasense/api/v1/equipments', {
        headers: {
            'Authorization': `Basic ${encodedCredentials}`
        }
    });
    
    let success = check(res, {
        'status is 200': (r) => r.status === 200,
    });
    
    if (!success) {
        errorRate.add(1);
    }
    
    sleep(1);
}