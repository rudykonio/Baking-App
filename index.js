console.log('main file');

const worker = new Worker('webworker.js');
worker.postMessage({ event: 'test' });
worker.onmessage = event => {
    const data = event.data;
    switch (data.namespace || data.event) {
        case 'test':
            console.log('main file got a message from webworker');
    }
}