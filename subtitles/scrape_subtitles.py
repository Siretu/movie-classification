import os
import urllib
import requests
from StringIO import StringIO
from zipfile import ZipFile, BadZipfile
from lxml import html, etree

language = "eng"
genre = "action"
movie_language = "english"
rating_limit = 8

url = "http://www.opensubtitles.org/en/search/sublanguageid-%s/searchonlymovies-on/genre-%s/movielanguage-%s/movieimdbratingsign-4/movieimdbrating-%d/subformat-srt/sort-6/asc-0/simplexml" % (language, genre, movie_language, rating_limit)

cookie = {'PHPSESSID': "99gemr304triuiv3vg59u7tbd1"}


def lev_dist(s1, s2):
    if len(s1) > len(s2):
        s1, s2 = s2, s1

    distances = range(len(s1) + 1)
    for i2, c2 in enumerate(s2):
        distances_ = [i2+1]
        for i1, c1 in enumerate(s1):
            if c1 == c2:
                distances_.append(distances[i1])
            else:
                distances_.append(1 + min((distances[i1], distances[i1 + 1], distances_[-1])))
        distances = distances_
    return distances[-1]

def get_download_links(url):
    page = requests.get(url, cookies=cookie)
    print url
    tree = etree.fromstring(page.content)
    subtitles = tree.xpath('//subtitle')
    download_links = [x.xpath("./download/text()")[0] for x in subtitles]
    names = [x.xpath("./movie/text()")[0] for x in subtitles]
    return zip(names, download_links)


def main(url):
    downloads = get_download_links(url)
    for d in downloads:
        #url = urllib.urlopen(d[1])
        url = requests.get(d[1], cookies=cookie)
        print "Reading %s" % d[1]
        try:
            data = url.content
            zipf = ZipFile(StringIO(data))
            for name in zipf.namelist():
                if name.endswith(".srt"):
                    print "Found: %s" % name
                    content = ""
                    with zipf.open(name) as f:
                        content = f.read()
                    with open("%s/%s" % (genre, name), "w") as f:
                        f.write(content)
        except BadZipfile:
            print "Critical error"


if __name__ == "__main__":
    a = "The Dark Knight Rises [2012] BRRip [Dual Audio] [Eng+Hindi] x264 bondfan007.srt"
    b = "The.Dark.Knight.Rises.2012.1080p.BluRay.H264.AAC-RARBG.eng.srt"
    c = "The.Dark.Knight.2008.1080p.Bluray.x264.anoXmous_.eng.srt"
    d = "The.Lord.of.the.Rings.The.Fellowship.of.the.Ring.2001.EXTENDED.1080p.BluRay.H264.AAC-RARBG.eng.srt"
    print lev_dist(a,b)
    print lev_dist(a,c)
    print lev_dist(a,d)
    main(url)
