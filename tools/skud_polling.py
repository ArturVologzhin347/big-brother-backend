import enum
import random
import time
import requests

API_URL = 'http://localhost:8080/api/skud'

class EventType(enum.Enum):
    ENTER = 'ENTER'
    EXIT = 'EXIT'

def main():
    try:
        while True:
            number = random.choice(['1'])
            type = random.choice([EventType.ENTER,EventType.EXIT])
            timestamp = round(time.time() * 1000)
            fetch(number, type, timestamp)
            time.sleep(1)
        
    except KeyboardInterrupt:
        print('Interrupted!')
        
def fetch(number, type: EventType, timestamp):
    r = requests.post(API_URL, json={
        'number': number,
        'type': type.name,
        'timestamp': timestamp
    })
    
    if(r.status_code == 200):
        print(f'SUCCESS number: {number}, type: {type}, timestamp: {timestamp}')
    else:
        print(f'FAILURE: status: {r.status_code}, message: {r.json}')
        

if __name__ == '__main__':
    main()
    