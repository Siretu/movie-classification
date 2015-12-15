import os
import sys

def valid_line(line):
    line = line.strip()
    if not line: return False
    if "-->" in line: return False
    try:
        i = int(line)
    except ValueError:
        return True
    return False

def main():
    directory = "."
    if len(sys.argv) > 1:
        directory = sys.argv[1]

    print "Cleaning %s" % directory

    for fname in os.listdir(directory):
        if fname.endswith(".srt"):
            content = ""
            with open(directory + "/" + fname) as f:
                content = f.read()
            lines = [x.strip() for x in content.split("\n") if valid_line(x)]
            with open(directory + "/" + fname.replace(".srt", ".stripped"), "w") as f:
                f.write("\n".join(lines))
        

if __name__ == "__main__":
    main()
