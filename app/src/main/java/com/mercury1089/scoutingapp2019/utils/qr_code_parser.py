import cv2
import qrtools
import csv
import time
from shutil import copyfile

def show_webcam():

    num_vals = 7 # number of values per game phase
    
    last_input = ""
    SETUP_LIST = 'setupList.csv'
    EVENT_LIST = 'eventList.csv'
    cam = cv2.VideoCapture(0)
    iAr = []
    file = "QRCodes\\123_01.png"
    while True:
        ret_val, img = cam.read()
        cv2.imshow('1089 Scouting Scanner', img)
        if cv2.waitKey(1) == 27: 
            break  # esc to quit
        cv2.imwrite(file, img)
        qr = qrtools.QR()
        qr.decode(file) 
        if qr.data != "NULL" and qr.data != last_input:
            iAr = qr.data.strip().split(",")
            scouter=iAr[0]
            match=iAr[1]
            team=iAr[2]
            dstfile = "QRCodes\\"+team+"_"+match+".png"
            copyfile(file, dstfile)
            chunks = lambda iAr, n=num_vals: [iAr[i:i+n] \
                                              for i in range(0, len(iAr), n)]
            with open(SETUP_LIST, 'ab+') as csvfile:
                csvWrite = csv.writer(csvfile, dialect='excel', delimiter=',')
                csvWrite.writerow(iAr[:15])
            with open(EVENT_LIST, 'ab+') as csvfile:
                csvWrite = csv.writer(csvfile, dialect='excel', delimiter=',')
                setupArr = [team,match]
                del iAr[:15]
                for chunk in chunks(iAr):
                    if len(chunk) == 7:
                        chunk.append(scouter)
                        chunk.append(time.time())
                        time.sleep(.001)
                        csvWrite.writerow(setupArr + chunk)
            last_input = qr.data
            print( "Saved - ", scouter, ":", team,":", match)
    cv2.destroyAllWindows()


def main():
    show_webcam()

if __name__ == '__main__':
    main()
