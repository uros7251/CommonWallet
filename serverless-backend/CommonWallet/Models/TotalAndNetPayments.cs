using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CommonWallet.Models
{
	public class TotalAndNetPayments
	{
        [JsonProperty("person_id")]
        public int PersonId { get; set; }

        [JsonProperty("name")]
        public string Name { get; set; }

        [JsonProperty("total")]
        public double? Total { get; set; }

        [JsonProperty("net")]
        public double? Net { get; set; }
    }
}
