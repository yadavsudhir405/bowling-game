# bowling-game
Scoring Bowling.
*
The game consists of 10 frames as shown above.  In each frame the player has
two opportunities to knock down 10 pins.  The score for the frame is the total
number of pins knocked down, plus bonuses for strikes and spares.

A spare is when the player knocks down all 10 pins in two tries.  The bonus for
that frame is the number of pins knocked down by the next roll.  So in frame 3
above, the score is 10 (the total number knocked down) plus a bonus of 5 (the
number of pins knocked down on the next roll.)

A strike is when the player knocks down all 10 pins on his first try.  The bonus
for that frame is the value of the next two balls rolled.

In the tenth frame a player who rolls a spare or strike is allowed to roll the extra
balls to complete the frame.  However no more than three balls can be rolled in
tenth frame.
#Sample Input:
* First Frame:
   1,4->5
* Second Frame:
  4,5->14
* Third Frame:
  6,4->29
* Fourth Frame:
  5,5->49
* Sixth Frame:
  10->60
* Seventh Frame:
  0,1->61
* Eighth Frame:
  7,3->77
* Ninth Frame:
  6,4->97
* Tenth Frame:
  2,2,6->121
