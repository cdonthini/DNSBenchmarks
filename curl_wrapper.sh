#/bin/bash

if [ $# -ne 1 ]; then
    echo "Usage ./curl_wrapper.sh <url>"
    exit
fi

mesg=`curl -s $1`
echo $mesg
echo $mesg | grep -o -E 'href="http([^"#]+)"' | cut -d'/' -f3 | sort | uniq | xargs dig > /dev/null
