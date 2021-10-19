from pybleno import *
import sys
import signal
from EchoCharacteristic import *

print('bleno - keyboard');

bleno = Bleno()

def onStateChange(state):
   print('on -> stateChange: ' + state);

   if (state == 'poweredOn'):
     bleno.startAdvertising('Keyboard', ['ec00'])
   else:
     bleno.stopAdvertising();

bleno.on('stateChange', onStateChange)

characteristic = EchoCharacteristic('ec0F')

def onAdvertisingStart(error):
    print('on -> advertisingStart: ' + ('error ' + error if error else 'success'));

    if not error:
        bleno.setServices([
            BlenoPrimaryService({
                'uuid': 'ec00',
                'characteristics': [
                    characteristic
                    ]
            })
        ])
bleno.on('advertisingStart', onAdvertisingStart)

bleno.start()

print ('Hit <ENTER> to disconnect')

while(True):
    d = 'a'
    if (sys.version_info > (3, 0)):
        d = input()
    else:
        d = raw_input()

    if characteristic._updateValueCallback:
        #data = array.array('B', [0] * 1)
        #writeUInt8(data, bytes(d, 'ascii'), 0)
        characteristic._updateValueCallback(bytes(d, 'ascii'))

bleno.stopAdvertising()
bleno.disconnect()

print ('terminated.')
sys.exit(1)
