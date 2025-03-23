# Project1_Casino_CS151_SP25

Our casino consists of a handful of the classics: Roulette, Slots, BlackJack, Poker (5-hand) and of course, Sports Betting. All of our cards based games have 6 different decks to choose from, leading to unpredictable hands but also much lower efficiency of card counting. All games are singleplayer, you versus the house. You start with $1000 and will get kicked out if you (1) win $1,000,000 total, (2) Drink more than 5 drinks or (3) Type EXIT at anytime.
They are usable as follows:
Roulette - Upon choosing the roulette option, one is prompted with how much money they want to bet. If you exceed the amount you currently have (you can use dollars or chips), you will be prompted to try again. Once in the game, you can choose to split up your initial bet among the 36 different numbers. Once you have chosen your bets, you will win 2x your money back on whatever number wins that you bet on and lose money on whichever ones do not win. There is no functionality for betting on colors. 

Slots - You can put up an initial bet and win a different multiple of money based on whatever winning combination you get (if any at all). All the winning combinations are equally likely, you only have to start up the slots, place an initial bet and you will be betting all of that on one roll.

BlackJack - Choose blackjack and post a bet (there is not functionality for doubling down) you will be dealt two cards and will be able to see one of the dealers. Continaul checks ensure that we will know if you have won or lost and that the dealer has not already won as you continue to try to reach 21. Aces have conditional logic to give you the best possible hand, being a 1 or 11 depending on whatever will get you to 21 or make you not exceed it. 

Poker - You will be dealt 5 cards and will have to post an initial bet to even be dealt the cards. Upon seeing your cards, you may increase your bet or stay the same. After betting, both of you will reveal your cards, your cards will be ranked and compared, and if you win, you get a 2x payout.

Sport Betting - Odds are predetermined and according to the likelihood of the team winning, you will get a payout if it occurs (for example, if a team is more likely to lose and they win, you get a higher payout)

Navigation is done through our UI which reads numbers to initialize an appropriate object and you also play through this very same UI which will prompt you for bets and output important information such as your cards and winnning numbers / combos.

Do not get too drunk, enjoy our games and try to win as much as you can (but not too much) !


[CS151-06 _ Project1 - Casino.pdf](https://github.com/user-attachments/files/19407492/CS151-06._.Project1.-.Casino.pdf)
