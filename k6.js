

import { check } from 'k6';
import { sleep } from 'k6';
import http from 'k6/http';





export const options = {
    vus: 100,
    duration: '5s',
};

function init() {
    http.get('https://71ca-2a03-d000-4226-4094-1595-9109-66de-7193.ngrok-free.app/api/clear');
    
    http.post('https://71ca-2a03-d000-4226-4094-1595-9109-66de-7193.ngrok-free.app/api/addOpenInterval?x1=0&x2=5');
    
    http.post('https://71ca-2a03-d000-4226-4094-1595-9109-66de-7193.ngrok-free.app/api/addOpenInterval?x1=10&x2=50');
    
    http.post('https://71ca-2a03-d000-4226-4094-1595-9109-66de-7193.ngrok-free.app/api/addOpenInterval?x1=-90&x2=500');
    
    http.post('https://71ca-2a03-d000-4226-4094-1595-9109-66de-7193.ngrok-free.app/api/addOpenInterval?x1=-3&x2=190');
    
    http.post('https://71ca-2a03-d000-4226-4094-1595-9109-66de-7193.ngrok-free.app/api/addOpenInterval?x1=0.3&x2=5.7');

    http.post('https://71ca-2a03-d000-4226-4094-1595-9109-66de-7193.ngrok-free.app/api/addClosedInterval?x1=1&x2=10');
    
    http.post('https://71ca-2a03-d000-4226-4094-1595-9109-66de-7193.ngrok-free.app/api/addClosedInterval?x1=-19&x2=1090');
    
    http.post('https://71ca-2a03-d000-4226-4094-1595-9109-66de-7193.ngrok-free.app/api/addClosedInterval?x1=-5&x2=109');
    
    http.post('https://71ca-2a03-d000-4226-4094-1595-9109-66de-7193.ngrok-free.app/api/addClosedInterval?x1=-1&x2=-10');
    
    http.post('https://71ca-2a03-d000-4226-4094-1595-9109-66de-7193.ngrok-free.app/api/addClosedInterval?x1=19&x2=1076');
}

export default function () {


   // init();
    http.get('https://71ca-2a03-d000-4226-4094-1595-9109-66de-7193.ngrok-free.app/api/list');
    
    for (let id = 1; id <= 10; id++) {

        const res = http.get(http.url`https://71ca-2a03-d000-4226-4094-1595-9109-66de-7193.ngrok-free.app/api/findClosest?x=7`);
        check(res, {
            'is status 200': (r) => r.status === 200,
        });

        const res1 = http.get(http.url`https://71ca-2a03-d000-4226-4094-1595-9109-66de-7193.ngrok-free.app/api/getIntersections`);
        check(res1, {
            'is status 200': (r) => r.status === 200,
        });
        sleep(1);
    }


}
