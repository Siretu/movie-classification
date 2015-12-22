import os
import sys

genres = ["horror", "romance", "action"]
INDENT = 110

def parse_name(s):
    name = s.split("/")[-1].replace("%20"," ")
    return name


def correct_classification(name):
    command = 'find .. -name "%s"' % name
    print command
    cmd = os.popen(command)
    output = cmd.read()
    for x in genres:
        if x in output:
            return x
    print output
    1/0
    return "none"

def classification(line):
    max_genre = ""
    max_score = -1
    fields = line.split()
    for i, x in enumerate(fields):
        if x in genres:
            if float(fields[i+1]) > max_score:
                max_score = float(fields[i+1])
                max_genre = x
    return max_genre

def main():
    if len(sys.argv) > 1:
        content = ""
        with open(sys.argv[1]) as f:
            content = f.read()
        result = []
        for line in content.split("\n"):
            if line:
                name = parse_name(line.split()[0])
                c1 = classification(line)
                c2 = correct_classification(name)
                result.append("%s%s%s\t\t\t%s" % (name, " " * (INDENT - len(name)), c1, c2))
        print "Name:" + " " * (INDENT - 8) + "Classified as:" + " " * 10 + "Correct genre:"
        print "\n".join(result)



if __name__ == "__main__":
    main()
