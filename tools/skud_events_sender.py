import enum
import requests
import logging
import time
import random

logging.basicConfig()
logging.root.setLevel(logging.NOTSET)
logger = logging.getLogger('skud')
logger.setLevel(logging.DEBUG)

API_URL = 'http://localhost:8080/api/'
ENDPOINT_URL = API_URL + 'skud/'

class EventType(enum.Enum):
    ENTER = "ENTER"
    EXIT = "EXIT"
    
        
def random_card_number():
    return str(random.randrange(1, 4))
    
def request(card_number, type: EventType):
    timestamp = round(time.time() * 1000)
    r = requests.post(ENDPOINT_URL, json={
                      "card_number": card_number,
                      "type": type.value,
                      "timestamp": timestamp
                  })
    
    if(r.status_code == 200):
        logger.info(card_number + " " + type.value + " " + str(timestamp))
    else:
        logger.warning(r.status_code)
        

def main():
    try:
        while True:
            rand_card_number = random_card_number()
            rand_type = random.choice([EventType.ENTER, EventType.EXIT])
            request(rand_card_number, rand_type)
            time.sleep(3) 
            
    except KeyboardInterrupt:
        logger.info('interrupted!')


if(__name__ == "__main__"):
    main()
    