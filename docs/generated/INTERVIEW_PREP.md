# 🎯 Interview Prep - Casino (CS151 Project)

**Last Updated:** 2026-04-29

---

## 60-90 Second STAR Pitch

### Situation
CS151 class project requiring a text-based casino simulation demonstrating OOP principles in Java.

### Task
Build a multi-game casino with proper game logic, user interface, and game state management for 5 classic casino games.

### Action
- Implemented **5 casino games:** Roulette, Slots, BlackJack, Poker (5-card), Sports Betting
- Designed **6-deck card system** for card games (reduces card counting effectiveness)
- Built **bank/chip management** with $1,000 starting balance
- Created **exit conditions:** Win $1M, drink 5+ drinks, or type EXIT
- Developed **text-based UI** for navigation and gameplay

### Result

| Feature | Status | Evidence |
|---------|--------|----------|
| 5 games | **Confirmed** | `README.md` |
| 6-deck system | **Confirmed** | `README.md:3` |
| Bank management | **Confirmed** | $1000 start |
| Exit conditions | **Confirmed** | 3 exit paths |

---

## Technical Deep Dive

### Games Implemented

**Roulette:**
- 36-number betting system
- Split bet across multiple numbers
- 2x payout on winning numbers

**Slots:**
- Single bet, single spin
- Multiple winning combinations
- Variable payout multipliers

**BlackJack:**
- Two-card initial deal
- Ace conditional logic (1 or 11)
- Dealer AI
- No double-down (simplified)

**Poker (5-Card):**
- Initial 5-card deal
- Betting rounds
- Hand ranking comparison
- 2x payout on win

**Sports Betting:**
- Predetermined odds
- Higher payouts for underdogs
- Team likelihood modeling

---

## Drill-Down Q&A

### Q1: "Why 6 decks for card games?"

**Answer (Confirmed):** Reduces card counting effectiveness while maintaining unpredictability. More realistic to actual casino conditions.

### Q2: "How does the Ace logic work in BlackJack?"

**Answer (Confirmed):** Conditional logic checks which value (1 or 11) gets closer to 21 without busting. Dynamically adjusts during gameplay.

### Q3: "What OOP principles did you use?"

**Answer (Inferred):** Separate classes for each game type, inheritance for common game behaviors, encapsulation of game state. UI class handles navigation.

### Q4: "How do exit conditions work?"

**Answer (Confirmed):** Three paths: (1) Win $1,000,000 total, (2) Drink more than 5 drinks, (3) Type EXIT at any time.

---

## Quick Reference

| Topic | Evidence |
|-------|----------|
| Game rules | `README.md` |
| Entry point | Navigation UI |
| Language | Java |
| Course | CS151 Spring 2025 |
