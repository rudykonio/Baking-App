console.log('worker file');
onmessage = event => {
    const data = event.data;
    switch (data.namespace || data.event) {
        case 'test':
            console.log('worker file got a message from main');
            postMessage({ event: 'test' });
    }
}