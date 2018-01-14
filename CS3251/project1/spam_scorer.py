class SpamScorer:
    def __init__(self, suspWordsFile):
        HOST = "localhost"
        self.suspiciousWords = open(suspWordsFile, 'r').read().split('\n')

    def spamScore(self, msg):
        numSus = 0
        susWords = []
        msgWords = msg.split(" ")
        for word in msgWords:
            if word in self.suspiciousWords:
                numSus += 1
                susWords.append(word)
        return (numSus, numSus/len(msgWords), susWords)
