#import cv2
'''
import qrtools

import csv

import time

from matplotlib.path import Path

import numpy as np

import matplotlib.pyplot as plt

import matplotlib.patches as patches

from shutil import copyfile


def show_webcam(mirror=False):

	tempString = ""

	SETUP_LIST = 'setupList.csv'

	EVENT_LIST = 'eventList.csv'

	inputString = ""

	cam = cv2.VideoCapture(0)

	iAr = []

	file = "QRCodes\\123_01.png"

	timeToWait = 0

	while True:

		ret_val, img = cam.read()

		camera_capture = img

		cv2.imshow('1089 Scouting Scanner', img)

		if cv2.waitKey(1) == 27: 

			break  # esc to quit

		time.sleep(timeToWait)

		timeToWait = 0

		cv2.imwrite(file, camera_capture)

		qr = qrtools.QR()

		# file = "QRCodes\\123_01.png"

		qr.decode(file) 

		if qr.data != "NULL" and qr.data != tempString:

			inputString = qr.data

#			inputString="Revanth,1,254,1738,2495,Red,Right,1L,C,1,1,0,S,0,0,SandStorm,Possesed,Cargo,,,,1,SandStorm,CargoShip,Cargo,Left,,,1,TeleOp,Possesed,Panel,,,,1,TeleOp,Rocket,Panel,Left,Near,Tier2,1,TeleOp,Possesed,Panel,,,,1,TeleOp,Missed,Panel,,,,1,TeleOp,Possesed,Panel,,,,1,TeleOp,Rocket,Panel,Left,Far,Tier2,1,TeleOp,Possesed,Cargo,,,,1,TeleOp,CargoShip,Cargo,Left,,,1"

			iAr = inputString.strip().split(",")

			print inputString

			scouter=iAr[0]

			match=iAr[1]

			team=iAr[2]

			dstfile = "QRCodes\\"+team+"_"+match+".png"

			copyfile(file, dstfile)

			chunks = lambda iAr, n=7: [iAr[i:i+n] for i in range(0, len(iAr), n)]

			with open(SETUP_LIST, 'ab+') as csvfile:

				csvWrite = csv.writer(csvfile, dialect='excel', delimiter=',')

#				print chunks(iAr)

#				print len(iAr)

				csvWrite.writerow(iAr[:15])

			with open(EVENT_LIST, 'ab+') as csvfile:

				csvWrite = csv.writer(csvfile, dialect='excel', delimiter=',')

				#csvWrite.writerow("hi");

				setupArr = [team,match]

				newEvent = iAr

				#print newEvent

				del newEvent[:15]

				#print newEvent

				chunklen = len(chunks(newEvent))

				#print chunklen

				#print chunks(newEvent)

				#for i in chunks(newEvent)[:chunklen-1]:

				for i in chunks(newEvent):

					#y=750-float(i[3])

					#x=float(i[2])

					#action=i[5]

						#if action == '4':

					#	i.append(region(x,y))

					#else:

					#	i.append("Other")

					if len(i) == 7:

						i.append(scouter)

						i.append(time.time())

						time.sleep(.001)

						#print i

						csvWrite.writerow(setupArr + i)

			tempString = qr.data

			print "Saved - ", scouter, ":", team,":", match 

#			s_img = cv2.imread("check.png", -1)

#			l_img = cv2.imread(file)

#			x_offset=y_offset=50

#			for c in range(0,3):

#				l_img[y_offset:y_offset+s_img.shape[0], x_offset:x_offset+s_img.shape[1], c] =  s_img[:,:,c] * (s_img[:,:,3]/255.0) +  l_img[y_offset:y_offset+s_img.shape[0], x_offset:x_offset+s_img.shape[1], c] * (1.0 - s_img[:,:,3]/255.0)

#			camera_capture = l_img

#			cv2.putText(camera_capture, 'This one!', (230, 50), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 0), 2, cv2.LINE_AA)

#			cv2.imshow('1089 Scouting Scanner', s_img)

#			time.sleep(2)

		else:

			camera_capture = img

	cv2.destroyAllWindows()


def main():

	show_webcam(mirror=True)
'''
#if __name__ == '__main__':
#	main()
